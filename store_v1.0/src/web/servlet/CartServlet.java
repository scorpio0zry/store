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
 * 购物车
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String addCart(HttpServletRequest req, HttpServletResponse resp){
		try{
			String pid = req.getParameter("pid");
			Integer count = Integer.parseInt(req.getParameter("count"));
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.findByPid(pid);
			//购物项目单
			CartItem cartItem = new CartItem(product,count);
			
			//在session中找到购物车
			Cart cart = getCart(req);
			cart.addCart(cartItem);
			//查看用户是否登录，若登录则将信息更新到数据库
			addCart(cartItem, req);
			//将购物车信息重新存到session中
			req.getSession().setAttribute("cart", cart);
			resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 清空购物车
	 * @param req
	 * @return
	 */
	public String clearCart(HttpServletRequest req, HttpServletResponse resp){
		try{
			Cart cart = getCart(req);
			cart.clearCart();
			req.getSession().setAttribute("cart", cart);
			//查看用户是否登录，若登录则将信息更新到数据库
			clearCart(req);
			resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//在session中 找到购物车信息
	private Cart getCart(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		return cart;
	}
	
	/**
	 * 移除购物订单信息  removeCart
	 */
	public String removeCart(HttpServletRequest req, HttpServletResponse resp){
		try{
		String pid = req.getParameter("pid");
		Cart cart = getCart(req);
		cart.removeCart(pid);
		//如果用户已经登录,移除购物订单信息  将信息存储到数据库中
		removeCart(pid,req);
		resp.sendRedirect(req.getContextPath() + "/jsp/cart.jsp");
		return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 更改购物单数量信息
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
				//如果用户已经登录，将修改的购物信息存储到数据库中
				updateCart(req,cartItem);
				Map<String, Double> map = new LinkedHashMap<String, Double>();
				map.put("subtotal", cartItem.getSubtotal());
				map.put("total", cart.getTotal());
				JSONObject json = JSONObject.fromObject(map);
				resp.getWriter().println(json.toString());
				
			}else{
				resp.getWriter().println(1); //count值小于0的时候
			}
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	//如果用户已经登录，将添加的购物信息存储到数据库中
	public void addCart(CartItem cartItem,HttpServletRequest req){
		User user = (User) req.getSession().getAttribute("user");
		if(user != null){
			CartService cs = (CartService) BeanFactory.getBean("CartService");
			cs.addCart(cartItem,user);
		}		
	}
	
	//如果用户已经登录,清空购物清单，将信息存储到数据库中
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
	//如果用户已经登录,移除购物订单信息  将信息存储到数据库中
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
	
	//如果用户已经登录，将修改的购物信息存储到数据库中
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
