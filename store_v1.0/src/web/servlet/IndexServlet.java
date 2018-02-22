package web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Product;
import com.service.ProductService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;

public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	public String index(HttpServletRequest req, HttpServletResponse resp){
		try{
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			//最新商品
			List<Product> newList = ps.findByNew();
			//热门商品
			List<Product> hotList = ps.findByHot();
			
			req.setAttribute("newList", newList);
			req.setAttribute("hotList", hotList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}

}
