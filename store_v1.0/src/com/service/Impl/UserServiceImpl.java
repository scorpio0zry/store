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
	 * ע���û�
	 * @throws SQLException 
	 */
	@Override
	public void save(User user) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		user.setUid(UUIDUtils.getUUID());
		user.setState(1); //1����δ����  2�����Ѽ���
		user.setCode(UUIDUtils.getUUID()+UUIDUtils.getUUID());
		ud.save(user);
	}
	
	/**
	 * �����ʼ�
	 */
	@Override
	public void mail(User user) {
		MailUtils.sendMail(user.getEmail(), user.getCode());
	}

	/**
	 * �����û�����״̬
	 * @throws SQLException 
	 */
	@Override
	public User findByCode(String code) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User user = ud.findByCode(code);
		return user;
	}
	
	/**
	 * �����û���Ϣ
	 * @throws SQLException 
	 */
	@Override
	public void update(User user) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		ud.update(user);
	}
	
	/**
	 * �û���¼
	 * @throws SQLException 
	 */
	@Override
	public User login(User user) throws SQLException {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		User findUser = ud.login(user);
		return findUser;
	}

}
