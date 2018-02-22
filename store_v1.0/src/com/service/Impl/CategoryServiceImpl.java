package com.service.Impl;

import java.sql.SQLException;
import java.util.List;




import com.dao.CategoryDao;
import com.domain.Category;
import com.service.CategoryService;
import com.utils.BeanFactory;
import com.utils.CacheUtils;
/**
 * ��Ʒ����Serviceʵ����
 * @author scorpio0zry
 *
 *
 */
public class CategoryServiceImpl implements CategoryService{

	@Override
	public List<Category> findAll() throws SQLException {
		//��ȡ�����ļ�
		//CacheManager cacheManager =CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//��ȡcache
		//Cache cache = cacheManager.getCache("categoryCache");
		//����elementԪ��
		//Element element = cache.get("list");
		//List<Category> list = null;
		//û�л�ȡ������Ԫ��
		List<Category> list = (List<Category>) CacheUtils.get("categoryCache", "list");
		if(list == null){
			CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
			list = cd.findAll();
			CacheUtils.save("categoryCache", "list", list);
		}
		return list;
		
		
	}

	@Override
	public void save(Category category) throws SQLException {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.save(category);
		
		CacheUtils.delete("categoryCache", "list");
	}

	@Override
	public Category findByCid(String cid) throws SQLException {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		Category category = cd.findByCid(cid);
		return category;
	}

	@Override
	public void update(Category category) throws SQLException {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.update(category);
		CacheUtils.delete("categoryCache", "list");
		
		
	}

	@Override
	public void delete(String cid) throws SQLException {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.delete(cid);
		CacheUtils.delete("categoryCache", "list");
	}

}
