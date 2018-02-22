package web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Category;
import com.domain.PageBean;
import com.domain.Product;
import com.service.CategoryService;
import com.service.ProductService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ��Ʒ��ҳ��ѯ findByPage
	 */
	public String findByPage(HttpServletRequest req, HttpServletResponse resp){
		try {
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			PageBean<Product> page = ps.findByPage(currPage);
			req.setAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";
	}
	
	/**
	 * ��ת�����ҳ���ִ�еķ���:
	 */
	public String saveUI(HttpServletRequest req,HttpServletResponse resp){
		// ��ѯ���з���:
		try{
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = categoryService.findAll();
		req.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}
	
	public String updateFlag(HttpServletRequest req,HttpServletResponse resp){
		try{
			// ���ղ���:
			String pid = req.getParameter("pid");
			Integer pflag = Integer.parseInt(req.getParameter("pflag"));
			// ����ҵ���:
			ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
			Product product = productService.findByPid(pid);
			product.setPflag(pflag);
			
			productService.update(product);
			if(pflag == 1){
				resp.sendRedirect(req.getContextPath()+"/AdminProductServlet?method=findByPage&currPage=1");
			}else{
				resp.sendRedirect(req.getContextPath()+"/AdminProductServlet?method=findByDown&currPage=1");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �¼���Ʒ����  findByDown
	 */
	public String findByDown(HttpServletRequest req,HttpServletResponse resp){
		try {
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			PageBean<Product> page = ps.findByDown(currPage);
			req.setAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/product/downlist.jsp";
	}

}
