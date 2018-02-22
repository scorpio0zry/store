package com.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dao.ProductDao;
import com.domain.Product;
import com.utils.BeanFactory;

public class ProductDaoImpl implements ProductDao {
	// 查找最新商品
	@Override
	public List<Product> findByNew() throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from product where pflag=? order by pdate desc limit ?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(
				Product.class), 0, 9);
		return list;
	}

	// 查找热门商品
	@Override
	public List<Product> findByHot() throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from product where pflag=? and is_hot=? order by pdate desc limit ?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(
				Product.class), 0, 1, 9);
		return list;
	}

	// 获取总的记录数
	@Override
	public int findCountByCid(String cid) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select count(*) from product where cid=? and pflag=?";
		Long totalCount = (Long) qr.query(sql, new ScalarHandler(), cid, 0);
		return totalCount.intValue();
	}

	// 获取每页显示数据
	@Override
	public List<Product> findPageByCid(String cid, int begin, int pageEach)
			throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from product where cid=? and pflag=? order by pdate desc limit ?,?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(
				Product.class), cid, 0, begin, pageEach);
		return list;
	}

	// 根据pid查找商品
	@Override
	public Product findByPid(String pid) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from product where pid = ?";
		Product product = qr.query(sql,
				new BeanHandler<Product>(Product.class), pid);
		return product;
	}

	// 查找cid
	@Override
	public String findCid(String pid) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select cid from product where pid = ?";
		String cid = (String) qr.query(sql, new ScalarHandler(), pid);
		return cid;
	}

	@Override
	public Integer findCount() throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select count(*) from product where pflag = 0";
		Long totalCount = (Long) qr.query(sql, new ScalarHandler());
		return totalCount.intValue();
	}

	@Override
	public List<Product> findPage(int begin, Integer pageEach)
			throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from product where pflag = 0 order by pdate desc limit ?,?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(
				Product.class), begin, pageEach);
		return list;
	}

	@Override
	public void save(Product product) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "insert into product values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { product.getPid(), product.getPname(),
				product.getMarket_price(), product.getShop_price(),
				product.getPimage(), product.getPdate(), product.getIs_hot(),
				product.getPdesc(), product.getPflag(),
				product.getCategory().getCid() };
		qr.update(sql, params);
	}

	@Override
	public void update(Product product) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "update product set pname = ?,market_price=?,shop_price=?,pimage=?,is_hot=?,pdesc= ?,pflag=? where pid = ?";
		Object[] params = { product.getPname(), product.getMarket_price(), product.getShop_price(),
				product.getPimage(),product.getIs_hot(), product.getPdesc(), product.getPflag(),product.getPid()
				 };
		qr.update(sql, params);
	}

	@Override
	public Integer findCountByDown() throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select count(*) from product where pflag = 1";
		Long totalCount = (Long) qr.query(sql, new ScalarHandler());
		return totalCount.intValue();
		
	}

	@Override
	public List<Product> findPageDown(int begin, Integer pageEach) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from product where pflag = 1 order by pdate desc limit ?,?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(
				Product.class), begin, pageEach);
		return list;
	}

	@Override
	public int findCountByName(String name) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select count(*) from product where pname like ? and pflag=?";
		Long totalCount = (Long) qr.query(sql, new ScalarHandler(), "%"+name+"%", 0);
		return totalCount.intValue();
	}

	@Override
	public List<Product> findPageByName(String name, int begin, int pageEach) throws SQLException {
		QueryRunner qr = BeanFactory.getQueryRunner("c3p0");
		String sql = "select * from product where pname like ? and pflag=? order by pdate desc limit ?,?";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(
				Product.class), "%"+name+"%", 0, begin, pageEach);
		return list;
	}

}
