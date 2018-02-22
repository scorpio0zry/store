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
 * ����ģʽ��
 * 1.����servlet����̳�BaseServlet
 * 2.ÿ��������봫��method=�� ����������Ҫִ�еķ���
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
		//��ȡ������
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String methodName = req.getParameter("method");
		if(methodName == null || "".equals(methodName)){
			resp.getWriter().println("method����ΪNull");
			return;
		}
		//��ȡclass����
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
