package com.dao.Impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dao.CartDao;
import com.dao.ProductDao;
import com.domain.CartItem;
import com.domain.Product;
import com.domain.User;
import com.utils.BeanFactory;
import com.utils.C3P0Utils;

public class CartDaoImpl implements CartDao {
	
	//为新注册激活用户创建购物车
	@Override
	public void createCart(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into cart values (?,?)";
		qr.update(sql, user.getUid(),0);
	}

	@Override
	public void addCart(Connection conn, CartItem cartItem, User user) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update cart set total = total + ? where uid = ?";
		qr.update(conn, sql, cartItem.getSubtotal(),user.getUid());
	}

	@Override
	public void addCartItem(Connection conn, CartItem cartItem, User user) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into cartitem values (?,?,?,?)";
		Object[] params = {user.getUid(),cartItem.getNum(),cartItem.getSubtotal(),cartItem.getProduct().getPid()};
		qr.update(conn, sql, params);
	}
	//清空购物车
	@Override
	public void clearCart(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "delete from cartitem where uid = ?";
		qr.update(sql,user.getUid());
	}

	@Override
	public Double findSubtotal(String pid,User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select subtotal from cartitem where uid =? and pid=?";
		Double subtotal = (Double) qr.query(sql, new ScalarHandler(), user.getUid(),pid);
		return subtotal;
	}

	@Override
	public void removeCart(Connection conn, Double subtotal, User user) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update cart set total = total - ? where uid = ?";
		qr.update(conn, sql, subtotal,user.getUid());
	}

	@Override
	public void removeCartItem(Connection conn, User user, String pid) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "delete from cartitem where uid = ? and pid = ?";
		qr.update(conn, sql, user.getUid(),pid);
	}


	@Override
	public void updateCartItem(Connection conn,CartItem cartItem,User user) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update cartitem set num = ?,subtotal = ? where uid = ? and pid = ?";
		Object[] params = {cartItem.getNum(),cartItem.getSubtotal(),user.getUid(),cartItem.getProduct().getPid()};
		qr.update(conn, sql, params);
	}

	@Override
	public List<CartItem> findByUid(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		String sql = "select * from cartitem where uid = ?";
		List<CartItem> list = qr.query(sql, new BeanListHandler<CartItem>(CartItem.class), user.getUid());
		for (CartItem cartItem : list) {
			cartItem.setUser(user);
			Product product = pd.findByPid(cartItem.getPid());
			cartItem.setProduct(product);
		}
		return list;
	}

	
	
	

}
