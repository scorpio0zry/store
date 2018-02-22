package com.service.Impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.dao.OrderDao;
import com.domain.Order;
import com.domain.OrderItem;
import com.domain.PageBean;
import com.domain.User;
import com.service.OrderService;
import com.utils.BeanFactory;
import com.utils.C3P0Utils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void save(Order order) {
		Connection conn = null;
		try {
		    conn = C3P0Utils.getConnection();
			conn.setAutoCommit(false);
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			od.saveOrder(conn,order);
			for (OrderItem orderItem : order.getList()) {
				od.saveOrderItem(conn,orderItem);
			}			
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			//如果出现错误，回滚
			DbUtils.rollbackAndCloseQuietly(conn);
		}
		
	}
	
	//查找Order
	@Override
	public PageBean<Order> findOrder(User user, Integer currPage) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		//分页
		PageBean<Order> page = new PageBean<Order>();
		//当前页
		page.setCurrPage(currPage);
		//每页记录数
		int pageEach = 5;
		page.setPageEach(pageEach);
		//总记录数
		int totalCount = od.findCount(user);
		page.setTotalCount(totalCount);
		//总页数
		Double totalPage = Math.ceil((double)totalCount/pageEach);
		page.setTotalPage(totalPage.intValue());
		//订单详情
		int begin = (currPage - 1) * pageEach;
		List<Order> list = od.findOrder(user,begin,pageEach);
		page.setList(list);
		return page;
	}
	
	//查询oid
	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		Order order = od.findByOid(oid);
		
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}

	@Override
	public PageBean<Order> findAllOrder(Integer currPage) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		//分页
		PageBean<Order> page = new PageBean<Order>();
		//当前页
		page.setCurrPage(currPage);
		//每页记录数
		int pageEach = 20;
		page.setPageEach(pageEach);
		//总记录数
		int totalCount = od.findAllCount();
		page.setTotalCount(totalCount);
		//总页数
		Double totalPage = Math.ceil((double)totalCount/pageEach);
		page.setTotalPage(totalPage.intValue());
		//订单详情
		int begin = (currPage - 1) * pageEach;
		List<Order> list = od.findAllOrder(begin,pageEach);
		page.setList(list);
		return page;
	}

	@Override
	public PageBean<Order> findByState(int pstate, Integer currPage) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		//分页
		PageBean<Order> page = new PageBean<Order>();
		//当前页
		page.setCurrPage(currPage);
		//每页记录数
		int pageEach = 20;
		page.setPageEach(pageEach);
		//总记录数
		int totalCount = od.findCount(pstate);
		page.setTotalCount(totalCount);
		//总页数
		Double totalPage = Math.ceil((double)totalCount/pageEach);
		page.setTotalPage(totalPage.intValue());
		//订单详情
		int begin = (currPage - 1) * pageEach;
		List<Order> list = od.findOrder(pstate,begin,pageEach);
		page.setList(list);
		return page;
	}

	@Override
	public void setState(String oid, Integer state) throws SQLException {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.setState(oid,state);
	}

}
