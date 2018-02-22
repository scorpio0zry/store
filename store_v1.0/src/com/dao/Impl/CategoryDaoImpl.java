package com.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dao.CategoryDao;
import com.domain.Category;
import com.utils.BeanFactory;
/**
 * 商品分类Dao实体类
 * @author scorpio0zry
 *
 *
 */
public class CategoryDaoImpl implements CategoryDao{
	
	/**
	 * 查询所有分类
	 * @throws SQLException 
	 */
	@Override
	public List<Category> findAll() throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from category";
		List<Category> list = qr.query(sql, new BeanListHandler<Category>(Category.class));
		
		return list;
	}

	@Override
	public Category findByCid(String cid) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from category where cid = ?";
		Category category = qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		return category;
	}

	@Override
	public void save(Category category) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "insert into category values (?,?)";
		qr.update(sql, category.getCid(),category.getCname());
		
	}

	@Override
	public void update(Category category) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "update category set cname=? where cid=?";
		qr.update(sql, category.getCname(),category.getCid());
	}

	@Override
	public void delete(String cid) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "update product set cid = NULL where cid = ?";
		qr.update(sql, cid);
		sql = "delete from category where cid = ?";
		qr.update(sql, cid);		
	}

}
