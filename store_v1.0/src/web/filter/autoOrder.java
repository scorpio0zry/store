package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.domain.User;

/**
 * 订单登陆
 */
public class autoOrder implements Filter {

    public autoOrder() {
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if(user == null){
			req.setAttribute("msg", "您还没有登录，没有访问权限！");
			req.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);;
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
