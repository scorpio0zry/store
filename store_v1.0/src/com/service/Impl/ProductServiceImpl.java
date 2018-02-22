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
	
	//����������Ʒ
	@Override
	public List<Product> findByNew() throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findByNew();
	}
	
	//����������Ʒ
	@Override
	public List<Product> findByHot() throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findByHot();
	}

	@Override
	public PageBean<Product> findByCid(String cid, Integer currPage) throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//����PageBean����
		PageBean<Product> page = new PageBean<Product>();
		//��ȡ��ǰҳ��
		page.setCurrPage(currPage);
		//��ȡ�ܼ�¼��
		int total = pd.findCountByCid(cid);
		page.setTotalCount(total);
		//��ȡÿҳ��¼�� 12
		int pageEach = 12;
		page.setPageEach(pageEach);
		//��ȡ��ҳ����
		Double totalCount = (double) total;
		Double totalPage = Math.ceil(totalCount/pageEach);
		page.setTotalPage(totalPage.intValue());
		//��ȡÿҳ����
		int begin = (currPage - 1) * pageEach;
		List<Product> list = pd.findPageByCid(cid,begin,pageEach);
		page.setList(list);
		return page;
	}
	
	//������Ʒpid������Ʒ
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
		//���õ�ǰҳ
		page.setCurrPage(currPage);
		//����ÿҳ��¼��
		Integer pageEach = 10;
		page.setPageEach(pageEach);
		//�����ܼ�¼��
		Integer totalCount = pd.findCount();
		page.setTotalCount(totalCount);
		//������ҳ��
		double count = totalCount;
		Double totalPage = Math.ceil(count/pageEach);
		page.setTotalPage(totalPage.intValue());
		//ÿҳ��ʾ��Ʒ��
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
		//���õ�ǰҳ
		page.setCurrPage(currPage);
		//����ÿҳ��¼��
		Integer pageEach = 10;
		page.setPageEach(pageEach);
		//�����ܼ�¼��
		Integer totalCount = pd.findCountByDown();
		page.setTotalCount(totalCount);
		//������ҳ��
		double count = totalCount;
		Double totalPage = Math.ceil(count/pageEach);
		page.setTotalPage(totalPage.intValue());
		//ÿҳ��ʾ��Ʒ��
		int begin = (currPage - 1) * pageEach;
		List<Product> list = pd.findPageDown(begin, pageEach);
		page.setList(list);
		return page;
	}

	@Override
	public PageBean<Product> findByName(String name, Integer currPage) throws SQLException {
		ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
		//����PageBean����
		PageBean<Product> page = new PageBean<Product>();
		//��ȡ��ǰҳ��
		page.setCurrPage(currPage);
		//��ȡ�ܼ�¼��
		int total = pd.findCountByName(name);
		page.setTotalCount(total);
		//��ȡÿҳ��¼�� 12
		int pageEach = 12;
		page.setPageEach(pageEach);
		//��ȡ��ҳ����
		Double totalCount = (double) total;
		Double totalPage = Math.ceil(totalCount/pageEach);
		page.setTotalPage(totalPage.intValue());
		//��ȡÿҳ����
		int begin = (currPage - 1) * pageEach;
		List<Product> list = pd.findPageByName(name,begin,pageEach);
		page.setList(list);
		return page;
	}
	
	
}
