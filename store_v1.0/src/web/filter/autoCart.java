package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.domain.Cart;
import com.domain.User;
import com.service.CartService;
import com.utils.BeanFactory;

/**
 * 查看用户是否登录
 */
public class autoCart implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if(user != null){
			Cart cart = (Cart) req.getSession().getAttribute("cart");
			//如果没有购物车信息则从数据库查询
			if(cart == null){
				CartService cs = (CartService) BeanFactory.getBean("CartService");
				//从数据库查询购物车信息
				cart = cs.findByUser(user);
				//将数据存入到session中
				req.getSession().setAttribute("cart", cart);
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
