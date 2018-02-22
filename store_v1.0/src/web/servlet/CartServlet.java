package web.servlet;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.domain.Cart;
import com.domain.CartItem;
import com.domain.Product;
import com.domain.User;
import com.service.CartService;
import com.service.ProductService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;

/**
 * ���ﳵ
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String addCart(HttpServletRequest req, HttpServletResponse resp){
		try{
			String pid = req.getParameter("pid");
			Integer count = Integer.parseInt(req.getParameter("count"));
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.findByPid(pid);
			//������Ŀ��
			CartItem cartItem = new CartItem(product,count);
			
			//��session���ҵ����ﳵ
			Cart cart = getCart(req);
			cart.addCart(cartItem);
			//�鿴�û��Ƿ��¼������¼����Ϣ���µ����ݿ�
			addCart(cartItem, req);
			//�����ﳵ��Ϣ���´浽session��
			req.getSession().setAttribute("cart", cart);
			resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * ��չ��ﳵ
	 * @param req
	 * @return
	 */
	public String clearCart(HttpServletRequest req, HttpServletResponse resp){
		try{
			Cart cart = getCart(req);
			cart.clearCart();
			req.getSession().setAttribute("cart", cart);
			//�鿴�û��Ƿ��¼������¼����Ϣ���µ����ݿ�
			clearCart(req);
			resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//��session�� �ҵ����ﳵ��Ϣ
	private Cart getCart(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		return cart;
	}
	
	/**
	 * �Ƴ����ﶩ����Ϣ  removeCart
	 */
	public String removeCart(HttpServletRequest req, HttpServletResponse resp){
		try{
		String pid = req.getParameter("pid");
		Cart cart = getCart(req);
		cart.removeCart(pid);
		//����û��Ѿ���¼,�Ƴ����ﶩ����Ϣ  ����Ϣ�洢�����ݿ���
		removeCart(pid,req);
		resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
		return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * ���Ĺ��ﵥ������Ϣ
	 */
	public String changeCount(HttpServletRequest req, HttpServletResponse resp){
		try{
			Integer count = 0;
			try{
				count = Integer.parseInt(req.getParameter("count"));
			}catch(Exception e){
				count = -1;
			}
			String pid = req.getParameter("pid");
			if(count >= 0){
				Cart cart = getCart(req);
				CartItem cartItem = cart.getMap().get(pid);
				cartItem.setNum(count);
				cartItem.resetSubtotal();
				cart.setTotal();
				//����û��Ѿ���¼�����޸ĵĹ�����Ϣ�洢�����ݿ���
				updateCart(req,cartItem);
				Map<String, Double> map = new LinkedHashMap<String, Double>();
				map.put("subtotal", cartItem.getSubtotal());
				map.put("total", cart.getTotal());
				JSONObject json = JSONObject.fromObject(map);
				resp.getWriter().println(json.toString());
				
			}else{
				resp.getWriter().println(1); //countֵС��0��ʱ��
			}
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//����û��Ѿ���¼������ӵĹ�����Ϣ�洢�����ݿ���
	public void addCart(CartItem cartItem,HttpServletRequest req){
		User user = (User) req.getSession().getAttribute("user");
		if(user != null){
			CartService cs = (CartService) BeanFactory.getBean("CartService");
			cs.addCart(cartItem,user);
		}		
	}
	
	//����û��Ѿ���¼,��չ����嵥������Ϣ�洢�����ݿ���
	public void clearCart(HttpServletRequest req){
		User user = (User) req.getSession().getAttribute("user");
		if(user != null){
			try{
				CartService cs = (CartService) BeanFactory.getBean("CartService");
				cs.clearCart(user);
			}catch(SQLException e){
				e.printStackTrace();
				throw new RuntimeException();
			}
		}	
	}
	//����û��Ѿ���¼,�Ƴ����ﶩ����Ϣ  ����Ϣ�洢�����ݿ���
	public void removeCart(String pid,HttpServletRequest req){
		User user = (User) req.getSession().getAttribute("user");
		if(user != null){
			try{
				CartService cs = (CartService) BeanFactory.getBean("CartService");
				cs.removeCart(user,pid);
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException();
			}
		}	
	}
	
	//����û��Ѿ���¼�����޸ĵĹ�����Ϣ�洢�����ݿ���
	public void updateCart(HttpServletRequest req,CartItem cartItem){
		User user = (User) req.getSession().getAttribute("user");
		if(user != null){
			try{
				CartService cs = (CartService) BeanFactory.getBean("CartService");
				cs.updateCart(user,cartItem);
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException();
			}
		}			
	}
}
