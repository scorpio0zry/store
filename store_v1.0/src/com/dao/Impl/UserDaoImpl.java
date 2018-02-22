package com.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.dao.UserDao;
import com.domain.User;
import com.utils.BeanFactory;

public class UserDaoImpl implements UserDao {

	public User findByName(String value) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from user where username = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), value);
		return user;
	}

	/**
	 * 注册用户
	 * 
	 * @throws SQLException
	 */
	@Override
	public void save(User user) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { user.getUid(), user.getUsername(),
				user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(),
				user.getState(), user.getCode() };
		qr.update(sql, params);
	}

	/**
	 * 更改用户 激活状态
	 * 
	 * @throws SQLException
	 */
	@Override
	public User findByCode(String code) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from user where code = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	/**
	 * 更新用户信息
	 * @throws SQLException 
	 */
	@Override
	public void update(User user) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		Object[] params =  {user.getUsername(),
				user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(),
				user.getState(), user.getCode(), user.getUid() };
		qr.update(sql, params);
	}
	
	/**
	 * 用户登录
	 * @throws SQLException 
	 */
	@Override
	public User login(User user) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from user where username=? and password=? and state = 2";
		User findUser = qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(),user.getPassword());
		return findUser;
	}
}
