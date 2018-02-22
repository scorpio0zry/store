package com.service;

import java.sql.SQLException;

import com.domain.User;

/**
 * 用户模块Service接口
 * @author scorpio0zry
 *
 *
 */
public interface UserService {

	public User findByName(String value) throws SQLException;

	public void save(User user) throws SQLException;
	
	public void mail(User user);

	public User findByCode(String code) throws SQLException;

	public void update(User user) throws SQLException;

	public User login(User user) throws SQLException;
}
