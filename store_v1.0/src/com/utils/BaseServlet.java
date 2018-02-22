package com.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @author scorpio0zry
 * 开发模式：
 * 1.所有servlet必须继承BaseServlet
 * 2.每次请求必须传入method=？ ？就是我们要执行的方法
 *
 */
public class BaseServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//获取方法名
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String methodName = req.getParameter("method");
		if(methodName == null || "".equals(methodName)){
			resp.getWriter().println("method不能为Null");
			return;
		}
		//获取class对象
		Class clazz = this.getClass();
		try {
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String path = (String)method.invoke(this,req,resp);
			if(path != null){
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	
}
