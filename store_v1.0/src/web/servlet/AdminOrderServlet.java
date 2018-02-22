package web.servlet;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.domain.Order;
import com.domain.PageBean;
import com.service.OrderService;
import com.utils.BaseServlet;
import com.utils.BeanFactory;

/**
 * ��̨���� 
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ѯ���ж��� findAll
	 */
	public String findAll(HttpServletRequest req,HttpServletResponse resp){
		// ����״̬:
				try{
					Integer currPage = Integer.parseInt(req.getParameter("currPage"));
					String state = req.getParameter("state");
					if("".equals(state)){
						state = null;
					}
					OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
					PageBean<Order> page = null;
					if(state == null){
						// ��ѯ���ж���:
						page = orderService.findAllOrder(currPage);
					}else{
						// ��״̬��ѯ����:
						int pstate = Integer.parseInt(state);
						page = orderService.findByState(pstate,currPage);
					}
					req.setAttribute("page", page);
					req.setAttribute("state", state);
				}catch(Exception e){
					e.printStackTrace();
				}
				return "/admin/order/list.jsp";
	}
	
	/**
	 * ���Ҷ���  findOid
	 */
	public String findOid(HttpServletRequest req,HttpServletResponse resp){
		try{
			String oid = req.getParameter("oid");
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.findByOid(oid);
			JSONObject json = JSONObject.fromObject(order);
			resp.getWriter().println(json.toString());
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	/**
	 * �ı䶩��״̬ changeState
	 */
	public String changeState(HttpServletRequest req,HttpServletResponse resp){
		try{
			String oid = req.getParameter("oid");
			Integer state = Integer.parseInt(req.getParameter("state"));
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			os.setState(oid,state);
			if(state == 2){
				resp.sendRedirect(req.getContextPath() + "/AdminOrderServlet?method=findAll&currPage=1&state="+state);
			}else if(state == 3){
				resp.sendRedirect(req.getContextPath() +"/OrderServlet?method=listOrder&currPage=1");
			}		
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
