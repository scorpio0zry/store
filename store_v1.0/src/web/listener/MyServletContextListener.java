package web.listener;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dao.CategoryDao;
import com.dao.Impl.CategoryDaoImpl;
import com.domain.Category;
import com.utils.CacheUtils;

public class MyServletContextListener implements ServletContextListener {

    public MyServletContextListener() {
        // TODO Auto-generated constructor stub
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	CategoryDao cd = new CategoryDaoImpl();
    	try {
			List<Category> list = cd.findAll();
			CacheUtils.save("categoryCache", "list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }
	
}
