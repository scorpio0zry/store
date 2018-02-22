package web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.domain.Category;
import com.service.CategoryService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;
/**
 * 商品分类 Servlet
 * @author scorpio0zry
 *
 *
 */
public class CategoryServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest req, HttpServletResponse resp){
		try {
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list = cs.findAll();
			JSONArray jsonArray = JSONArray.fromObject(list);
			resp.getWriter().println(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return null;
	}
}
