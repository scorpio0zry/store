package web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Category;
import com.service.CategoryService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;
import com.utils.UUIDUtils;

/**
 * ��̨�������
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * ������� findAll
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp){
		try{
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list = cs.findAll();
			req.setAttribute("list", list);
			return "/admin/category/list.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	/**
	 * ������� save
	 */
	public String save(HttpServletRequest req, HttpServletResponse resp){
		try{
		String cname = req.getParameter("cname");
		Category category = new Category();
		category.setCid(UUIDUtils.getUUID());
		category.setCname(cname);
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		cs.save(category);
		resp.sendRedirect(req.getContextPath() + "/AdminCategoryServlet?method=findAll");
		return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * �༭ҳ�� edit
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp){
		try{
			String cid = req.getParameter("cid");
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			Category category = cs.findByCid(cid);
			req.setAttribute("category", category);
			return "/admin/category/edit.jsp";
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * ���ݿ��޸ķ��� update
	 */
	public String update(HttpServletRequest req, HttpServletResponse resp){
		try{
			String cid = req.getParameter("cid");
			String cname = req.getParameter("cname");
			Category category = new Category();
			category.setCid(cid);
			category.setCname(cname);
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.update(category);
			resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * ���ݿ���ɾ������ delete
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp){
		try{
			String cid = req.getParameter("cid");
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.delete(cid);
			resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
