package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.domain.Order;
import com.domain.OrderItem;
import com.domain.User;

public interface OrderDao {

	void saveOrder(Connection conn, Order order) throws SQLException;

	void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException;

	int findCount(User user) throws SQLException;

	List<Order> findOrder(User user, Integer begin, int pageEach) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws SQLException;

	int findAllCount() throws SQLException;

	List<Order> findAllOrder(int begin, int pageEach) throws Exception;

	int findCount(int pstate) throws SQLException;

	List<Order> findOrder(int pstate, int begin, int pageEach) throws Exception;

	void setState(String oid, Integer state) throws SQLException;

}
