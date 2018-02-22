package com.service.Impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.dao.CartDao;
import com.domain.Cart;
import com.domain.CartItem;
import com.domain.User;
import com.service.CartService;
import com.utils.BeanFactory;
import com.utils.C3P0Utils;

public class CartServiceImpl implements CartService {
	//为注册新用户创建购物车
	@Override
	public void createCart(User user) throws SQLException {
		CartDao cd = (CartDao) BeanFactory.getBean("CartDao");
		cd.createCart(user);
	}	
	
	//将购物车的数据存入到数据库
	@Override
	public void addCart(CartItem cartItem,User user) {
		//开启事务
		Connection conn = null;
		try {
			conn = C3P0Utils.getConnection();
			conn.setAutoCommit(false);
			CartDao cd = (CartDao) BeanFactory.getBean("CartDao");
			//将数据存入cart表中
			cd.addCart(conn,cartItem,user);
			//将数据存入cartItem表中
			cd.addCartItem(conn,cartItem,user);
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
	}
	
	//清空购物车
	@Override
	public void clearCart(User user) throws SQLException {
		CartDao cd = (CartDao) BeanFactory.getBean("CartDao");
		cd.clearCart(user);
	}
	
	//移除购物订单信息
	@Override
	public void removeCart(User user, String pid) throws SQLException {
		Connection conn = null;
		try{
			conn = C3P0Utils.getConnection();
			conn.setAutoCommit(false);
			CartDao cd = (CartDao) BeanFactory.getBean("CartDao");
			Double subtotal = cd.findSubtotal(pid,user);
			cd.removeCart(conn,subtotal,user);
			cd.removeCartItem(conn,user,pid);
			
			DbUtils.commitAndCloseQuietly(conn);
		}catch(Exception e){
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
		
	}
	
	//修改购物车信息
	@Override
	public void updateCart(User user, CartItem cartItem) {
		Connection conn = null;
		try{
			conn = C3P0Utils.getConnection();
			conn.setAutoCommit(false);
			CartDao cd = (CartDao) BeanFactory.getBean("CartDao");
			Double subtotal = cd.findSubtotal(cartItem.getProduct().getPid(),user);
			cd.removeCart(conn, subtotal, user);
			cd.addCart(conn,cartItem,user);
			cd.updateCartItem(conn,cartItem,user);
			
			DbUtils.commitAndCloseQuietly(conn);
		}catch(Exception e){
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
	}
	
	//从数据库查询Cart信息
	@Override
	public Cart findByUser(User user) {
		CartDao cd = (CartDao) BeanFactory.getBean("CartDao");
		Cart cart = null;
		try{
			cart = new Cart();
			cart.setUser(user);
			Map<String,CartItem> map = cart.getMap();
			List<CartItem> list = cd.findByUid(user);
			for (CartItem cartItem : list) {
				String pid = cartItem.getProduct().getPid();
				map.put(pid, cartItem);
			}
			cart.setTotal();
			return cart;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
