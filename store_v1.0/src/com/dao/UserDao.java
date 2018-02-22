package com.dao;

import java.sql.SQLException;

import com.domain.User;

/**
 * �û�ģ��Dao�ӿ�
 * @author scorpio0zry
 *
 *
 */
public interface UserDao {
	public User findByName(String value) throws SQLException;

	public void save(User user) throws SQLException;

	public User findByCode(String code) throws SQLException;

	public void update(User user) throws SQLException;

	public User login(User user) throws SQLException;
}
