package com.service;

import java.sql.SQLException;

import com.domain.Cart;
import com.domain.CartItem;
import com.domain.User;

public interface CartService {

	void addCart(CartItem cartItem, User user);

	void createCart(User user) throws SQLException;

	void clearCart(User user) throws SQLException;

	void removeCart(User user, String pid) throws SQLException;

	void updateCart(User user, CartItem cartItem);

	Cart findByUser(User user);

}
