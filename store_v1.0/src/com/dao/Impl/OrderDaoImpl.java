package com.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dao.OrderDao;
import com.domain.Order;
import com.domain.OrderItem;
import com.domain.Product;
import com.domain.User;
import com.utils.C3P0Utils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(new java.util.Date());
		Timestamp ts = Timestamp.valueOf(nowTime);
//		Timestamp ts = new Timestamp(order.getOrdertime().getTime());
		Object[] params = { order.getOid(), ts,
				order.getTotal(), order.getState(), order.getAddress(),
				order.getName(), order.getTelephone(), order.getUser().getUid() };
		qr.update(conn, sql, params);
		
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values (?,?,?,?,?)";
		Object[] params = { orderItem.getItemId(), orderItem.getCount(),
				orderItem.getSubtotal(), orderItem.getProduct().getPid(),
				orderItem.getOrder().getOid() };
		qr.update(conn, sql, params);
	}

	//查找总记录数
	@Override
	public int findCount(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		Long totalCount = (Long) qr.query(sql, new ScalarHandler(), user.getUid());
		return totalCount.intValue();
	}
	
	//查找订单
	@Override
	public List<Order> findOrder(User user, Integer begin, int pageEach) throws Exception {
		//根据uid查找当前订单
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		Connection conn = C3P0Utils.getConnection();
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(),begin,pageEach);
		/*//为了读取时、分、秒 单独读取一下
		sql = "select ordertime from orders where uid = ? order by ordertime desc limit ?,?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUid());
		ps.setInt(2, begin);
		ps.setInt(3, pageEach);
		ResultSet rs = ps.executeQuery();
		int index = 0;
		while(rs.next()){
			Timestamp ts = rs.getTimestamp("ordertime");
			java.util.Date date = new java.util.Date(ts.getTime());
			list.get(index).setOrdertime(date);
			index++;		
		}*/
		for (Order order : list) {
			sql = "select * from orderitem o,product p where p.pid = o.pid and o.oid = ?";
			List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : listMap) {
				Product product = new Product();
				BeanUtils.populate(product, map);
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				orderItem.setProduct(product);
				order.getList().add(orderItem);
			}
		}
		return list;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		sql = "select * from orderitem o,product p where o.pid = p.pid and oid = ?";
		List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : listMap) {
			Product product = new Product();
			BeanUtils.populate(product, map);
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			
			orderItem.setProduct(product);
			order.getList().add(orderItem);
		}
		
		return order;
	}

	@Override
	public void update(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update orders set total=?,state=?,address=?,name=?,telephone=? where oid=?";
		Object[] params = {order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		qr.update(sql, params);
	}

	@Override
	public int findAllCount() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from orders";
		Long count = (Long) qr.query(sql, new ScalarHandler());
		return count.intValue();
	}

	@Override
	public List<Order> findAllOrder(int begin, int pageEach) throws Exception {
		//根据uid查找当前订单
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),begin,pageEach);
		for (Order order : list) {
			sql = "select * from orderitem o,product p where p.pid = o.pid and o.oid = ?";
			List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : listMap) {
				Product product = new Product();
				BeanUtils.populate(product, map);
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				orderItem.setProduct(product);
				order.getList().add(orderItem);
			}
		}
		return list;
	}

	@Override
	public int findCount(int pstate) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from orders where state = ?";
		Long count = (Long) qr.query(sql, new ScalarHandler(),pstate);
		return count.intValue();
	}

	@Override
	public List<Order> findOrder(int pstate, int begin, int pageEach) throws Exception {
		//根据uid查找当前订单
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from orders where state = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),pstate,begin,pageEach);
		for (Order order : list) {
			sql = "select * from orderitem o,product p where p.pid = o.pid and o.oid = ?";
			List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : listMap) {
				Product product = new Product();
				BeanUtils.populate(product, map);
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				orderItem.setProduct(product);
				order.getList().add(orderItem);
			}
		}
		return list;
	}
	
	/**
	 * 更改订单状态
	 * @throws SQLException 
	 */
	@Override
	public void setState(String oid, Integer state) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update orders set state = ? where oid = ?";
		qr.update(sql, state + 1,oid);
	}

}
