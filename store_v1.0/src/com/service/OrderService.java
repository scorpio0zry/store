package com.service;


import java.sql.SQLException;

import com.domain.Order;
import com.domain.PageBean;
import com.domain.User;

public interface OrderService {

	void save(Order order);

	PageBean<Order> findOrder(User user, Integer currPage) throws Exception;

	Order findByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	PageBean<Order> findAllOrder(Integer currPage) throws Exception;

	PageBean<Order> findByState(int pstate, Integer currPage) throws Exception;

	void setState(String oid, Integer state) throws SQLException;

}
