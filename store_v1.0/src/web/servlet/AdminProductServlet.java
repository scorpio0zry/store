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
	 * 商品分页查询 findByPage
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
	 * 跳转到添加页面的执行的方法:
	 */
	public String saveUI(HttpServletRequest req,HttpServletResponse resp){
		// 查询所有分类:
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
			// 接收参数:
			String pid = req.getParameter("pid");
			Integer pflag = Integer.parseInt(req.getParameter("pflag"));
			// 调用业务层:
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
	 * 下架商品管理  findByDown
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
