package com.service.Impl;

import java.sql.SQLException;
import java.util.List;

import com.dao.CategoryDao;
import com.dao.ProductDao;
import com.domain.Category;
import com.domain.PageBean;
import com.domain.Product;
import com.service.ProductService;
import com.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
	
	//查找最新商品
	@Override
	public List<Product> findByNew() throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findByNew();
	}
	
	//查找热门商品
	@Override
	public List<Product> findByHot() throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findByHot();
	}

	@Override
	public PageBean<Product> findByCid(String cid, Integer currPage) throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//创建PageBean对象
		PageBean<Product> page = new PageBean<Product>();
		//获取当前页面
		page.setCurrPage(currPage);
		//获取总记录数
		int total = pd.findCountByCid(cid);
		page.setTotalCount(total);
		//获取每页记录数 12
		int pageEach = 12;
		page.setPageEach(pageEach);
		//获取总页面数
		Double totalCount = (double) total;
		Double totalPage = Math.ceil(totalCount/pageEach);
		page.setTotalPage(totalPage.intValue());
		//获取每页数据
		int begin = (currPage - 1) * pageEach;
		List<Product> list = pd.findPageByCid(cid,begin,pageEach);
		page.setList(list);
		return page;
	}
	
	//根据商品pid查找商品
	@Override
	public Product findByPid(String pid) throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		Product product = pd.findByPid(pid);
		String cid = pd.findCid(pid);
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		Category category = cd.findByCid(cid);
		product.setCategory(category);
		return product;
	}

	@Override
	public PageBean<Product> findByPage(Integer currPage) throws SQLException {
		PageBean<Product> page = new PageBean<Product>();
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//设置当前页
		page.setCurrPage(currPage);
		//设置每页记录数
		Integer pageEach = 10;
		page.setPageEach(pageEach);
		//设置总记录数
		Integer totalCount = pd.findCount();
		page.setTotalCount(totalCount);
		//设置总页数
		double count = totalCount;
		Double totalPage = Math.ceil(count/pageEach);
		page.setTotalPage(totalPage.intValue());
		//每页显示商品数
		int begin = (currPage - 1) * pageEach;
		List<Product> list = pd.findPage(begin, pageEach);
		page.setList(list);
		return page;
	}

	@Override
	public void save(Product product) throws SQLException {
		ProductDao pd =(ProductDao) BeanFactory.getBean("ProductDao");
		pd.save(product);
	}

	@Override
	public void update(Product product) throws SQLException {
		ProductDao pd =(ProductDao) BeanFactory.getBean("ProductDao");
		pd.update(product);
	}

	@Override
	public PageBean<Product> findByDown(Integer currPage) throws SQLException {
		PageBean<Product> page = new PageBean<Product>();
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//设置当前页
		page.setCurrPage(currPage);
		//设置每页记录数
		Integer pageEach = 10;
		page.setPageEach(pageEach);
		//设置总记录数
		Integer totalCount = pd.findCountByDown();
		page.setTotalCount(totalCount);
		//设置总页数
		double count = totalCount;
		Double totalPage = Math.ceil(count/pageEach);
		page.setTotalPage(totalPage.intValue());
		//每页显示商品数
		int begin = (currPage - 1) * pageEach;
		List<Product> list = pd.findPageDown(begin, pageEach);
		page.setList(list);
		return page;
	}

	@Override
	public PageBean<Product> findByName(String name, Integer currPage) throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//创建PageBean对象
		PageBean<Product> page = new PageBean<Product>();
		//获取当前页面
		page.setCurrPage(currPage);
		//获取总记录数
		int total = pd.findCountByName(name);
		page.setTotalCount(total);
		//获取每页记录数 12
		int pageEach = 12;
		page.setPageEach(pageEach);
		//获取总页面数
		Double totalCount = (double) total;
		Double totalPage = Math.ceil(totalCount/pageEach);
		page.setTotalPage(totalPage.intValue());
		//获取每页数据
		int begin = (currPage - 1) * pageEach;
		List<Product> list = pd.findPageByName(name,begin,pageEach);
		page.setList(list);
		return page;
	}
	
	
}
