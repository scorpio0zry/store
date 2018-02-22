package web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.domain.User;
import com.service.UserService;
import com.service.Impl.UserServiceImpl;
import com.utils.CookieUtils;

/**
 * ×Ô¶¯µÇÂ¼¹ýÂËÆ÷
 * @author scorpio0zry
 *
 *
 */

public class autoLoginFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if(user == null){
			String auto = CookieUtils.findCookie(req.getCookies(), "autoLogin");
			if(auto != null){
				String username = auto.split("#")[0];
				String password = auto.split("#")[1];
				User newUser = new User();
				newUser.setUsername(username);
				newUser.setPassword(password);
				UserService us = new UserServiceImpl();
				try {
					User user2 = us.login(newUser);
					req.getSession().setAttribute("user", user2);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
			}			
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
