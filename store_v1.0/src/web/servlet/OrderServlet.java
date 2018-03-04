package web.servlet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.domain.Cart;
import com.domain.CartItem;
import com.domain.Order;
import com.domain.OrderItem;
import com.domain.PageBean;
import com.domain.Pay;
import com.domain.PayBack;
import com.domain.User;
import com.itheima.store.utils.PropertiesUtils;
import com.service.CartService;
import com.service.OrderService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;
import com.utils.PaymentUtil;
import com.utils.UUIDUtils;

/**
 * 订单Servlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 存储订单
	 */
	public String saveOrder(HttpServletRequest req, HttpServletResponse resp){
		try{
			Order order = new Order();
			order.setOid(UUIDUtils.getUUID());
			
			order.setState(1);// 1未付款
			Cart cart = (Cart) req.getSession().getAttribute("cart");
			//如果购物车没有东西
			if(cart == null){
				req.setAttribute("msg", "您还没有购物");
				return "/jsp/msg.jsp";
			}
			order.setTotal(cart.getTotal());
			User user = (User) req.getSession().getAttribute("user");
			//用户没有登录
			if(user == null){
				req.setAttribute("msg", "您还没有登录");
				return "/jsp/login.jsp";
			}
			order.setUser(user);
			for (CartItem cartItem : cart.getMap().values()) {
				//创建订单项
				OrderItem orderItem = new OrderItem();
				orderItem.setItemId(UUIDUtils.getUUID());
				orderItem.setProduct(cartItem.getProduct());
				orderItem.setCount(cartItem.getNum());
				orderItem.setSubtotal(cartItem.getSubtotal());
				orderItem.setOrder(order);
				
				//将订单项存入订单中
				order.getList().add(orderItem);
			}
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			os.save(order);
			
			//清空购物车
			cart.clearCart();
			
			//将数据中的购物车信息清空
			CartService cs = (CartService) BeanFactory.getBean("CartService");
			cs.clearCart(user);
		
						
			
			return "OrderServlet?method=findByOid&oid=" + order.getOid();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 查询订单  listOrder
	 */
	public String listOrder(HttpServletRequest req, HttpServletResponse resp){
		//获取当前页
		try{
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			User user = (User) req.getSession().getAttribute("user");
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			PageBean<Order> page = os.findOrder(user,currPage);
			req.setAttribute("page", page);
		
			return "/jsp/order_list.jsp";
		}catch(Exception e){
			return "404.jsp";
		}
	}
	
	/**
	 * 查找订单  findByOid
	 */
	public String findByOid(HttpServletRequest req, HttpServletResponse resp){
		try{
			String oid = req.getParameter("oid");
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.findByOid(oid);
			req.setAttribute("order", order);
			return "/jsp/order_info.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * 跳转支付页面  payOrder
	 */
	public String payOrder(HttpServletRequest req, HttpServletResponse resp){
		try{
			String oid = req.getParameter("oid");
			String address = req.getParameter("address");
			String name = req.getParameter("name");
			String telephone = req.getParameter("telephone");
			String pd_FrpId = req.getParameter("pd_FrpId");  //银行信息编码
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.findByOid(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			os.updateOrder(order);
			
			//支付
			String p0_Cmd = "Buy";
			String p1_MerId = PropertiesUtils.getProperties("merchantInfo.properties", "p1_MerId");
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = PropertiesUtils.getProperties("merchantInfo.properties", "responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			String keyValue = PropertiesUtils.getProperties("merchantInfo.properties", "keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue); 
			Pay pay = new Pay(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pr_NeedResponse, keyValue, pd_FrpId, hmac);
			req.setAttribute("pay", pay);
			
			return "/jsp/submitpay.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 返回支付信息  callBack
	 */
	public String callBack(HttpServletRequest req, HttpServletResponse resp){
		try{
		Map<String,String[]> map = req.getParameterMap();
		String keyValue = PropertiesUtils.getProperties("merchantInfo.properties", "keyValue");
		PayBack payBack = new PayBack();
		BeanUtils.populate(payBack, map);
		boolean flag = PaymentUtil.verifyCallback(payBack.getHmac(), payBack.getP1_MerId(), payBack.getR0_Cmd(), payBack.getR1_Code(), payBack.getR2_TrxId(), payBack.getR3_Amt(), payBack.getR4_Cur(), payBack.getR5_Pid(), payBack.getR6_Order(), payBack.getR7_Uid(), payBack.getR8_MP(), payBack.getR9_BType(), keyValue);
		if(flag){
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.findByOid(payBack.getR6_Order());
			order.setState(2);
			os.updateOrder(order);
			
			req.setAttribute("msg", "您的订单号"+payBack.getR6_Order()+"已付款，付款金额为"+order.getTotal()+"元");
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/msg.jsp";
		
	}
}
