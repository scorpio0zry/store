package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.domain.CartItem;
import com.domain.User;


public interface CartDao {

	void createCart(User user) throws SQLException;

	void addCart(Connection conn, CartItem cartItem, User user) throws SQLException;

	void addCartItem(Connection conn, CartItem cartItem, User user) throws SQLException;

	void clearCart(User user) throws SQLException;

	Double findSubtotal(String pid,User user) throws SQLException;

	void removeCart(Connection conn, Double subtotal, User user) throws SQLException;

	void removeCartItem(Connection conn, User user, String pid) throws SQLException;

	void updateCartItem(Connection conn, CartItem cartItem, User user) throws SQLException;

	List<CartItem> findByUid(User user) throws SQLException;



}
