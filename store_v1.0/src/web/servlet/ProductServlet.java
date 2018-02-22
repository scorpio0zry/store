package web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.PageBean;
import com.domain.Product;
import com.service.ProductService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;
import com.utils.CookieUtils;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分页查找页面
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findPage(HttpServletRequest req,HttpServletResponse resp){
		try{
			String cid = req.getParameter("cid");
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			PageBean<Product> page = ps.findByCid(cid,currPage);
			req.setAttribute("page", page);
			return "/jsp/product_list.jsp";
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查找商品 findProduct
	 */
	public String findProduct(HttpServletRequest req,HttpServletResponse resp){
		try {
			String pid = req.getParameter("pid");
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.findByPid(pid);
			req.setAttribute("product", product);
			saveCookie(req,resp,pid);
			return "/jsp/product_info.jsp";
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	//浏览历史记录,将浏览过的商品pid存到cookie中
	private void saveCookie(HttpServletRequest req, HttpServletResponse resp, String pid) {
		Cookie[] cookies = req.getCookies();
		String value = CookieUtils.findCookie(cookies, "history");
		if(value == null){
			Cookie cookie = new Cookie("history", pid);
			cookie.setPath(req.getContextPath());
			cookie.setMaxAge(7*24*60*60);
			resp.addCookie(cookie);
		}else{
			String[] ids = value.split("-");
			LinkedList<String> list = new LinkedList<String>(Arrays.asList(ids));
			if(list.contains(pid)){
				list.remove(pid);
				list.addFirst(pid);
			}else{
				if(list.size()>=6){
					list.removeLast();
					list.addFirst(pid);
				}else{
					list.addFirst(pid);
				}
			}
			
			StringBuffer sb = new StringBuffer();
			for (String id : list) {
				sb.append(id).append("-");
			}
			value = sb.toString().substring(0, sb.length()-1);
			Cookie cookie = new Cookie("history", value);
			cookie.setPath(req.getContextPath());
			cookie.setMaxAge(7*24*60*60);
			resp.addCookie(cookie);
		}
	}
	
	//清除浏览记录 clearRecord
	public String clearRecord(HttpServletRequest req,HttpServletResponse resp){
		String cid = req.getParameter("cid");
		Cookie cookie = new Cookie("history", "");
		cookie.setPath(req.getContextPath());
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		try {
			resp.sendRedirect(req.getContextPath() + "/ProductServlet?method=findPage&currPage=1&cid="+cid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据名称查询商品 findByName
	 */
	public String findByName(HttpServletRequest req,HttpServletResponse resp){
		try{
			req.setCharacterEncoding("utf-8");
			String name = req.getParameter("name");
			PageBean<Product> page = null;
			if(name != null && name != ""){
				Integer currPage = 1;
				ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
				page = ps.findByName(name,currPage);
			}
			
			req.setAttribute("page", page);
			return "/jsp/product_list.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
}
