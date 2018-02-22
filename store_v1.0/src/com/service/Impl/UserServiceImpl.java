package com.service.Impl;

import java.sql.SQLException;

import com.dao.UserDao;
import com.domain.User;
import com.service.UserService;
import com.utils.BeanFactory;
import com.utils.MailUtils;
import com.utils.UUIDUtils;

public class UserServiceImpl implements UserService {

	public User findByName(String value) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User user = ud.findByName(value);
		return user;
	}
	
	/**
	 * 注册用户
	 * @throws SQLException 
	 */
	@Override
	public void save(User user) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		user.setUid(UUIDUtils.getUUID());
		user.setState(1); //1代表未激活  2代表已激活
		user.setCode(UUIDUtils.getUUID()+UUIDUtils.getUUID());
		ud.save(user);
	}
	
	/**
	 * 发送邮件
	 */
	@Override
	public void mail(User user) {
		MailUtils.sendMail(user.getEmail(), user.getCode());
	}

	/**
	 * 更改用户激活状态
	 * @throws SQLException 
	 */
	@Override
	public User findByCode(String code) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User user = ud.findByCode(code);
		return user;
	}
	
	/**
	 * 更新用户信息
	 * @throws SQLException 
	 */
	@Override
	public void update(User user) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		ud.update(user);
	}
	
	/**
	 * 用户登录
	 * @throws SQLException 
	 */
	@Override
	public User login(User user) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User findUser = ud.login(user);
		return findUser;
	}

}
