package com.service;

import java.sql.SQLException;
import java.util.List;





import com.domain.PageBean;
import com.domain.Product;

public interface ProductService {

	List<Product> findByNew() throws SQLException;

	List<Product> findByHot() throws SQLException;

	PageBean<Product> findByCid(String cid, Integer currPage) throws SQLException;

	Product findByPid(String pid) throws SQLException;

	PageBean<Product> findByPage(Integer currPage) throws SQLException;

	void save(Product product) throws SQLException;

	void update(Product product) throws SQLException;

	PageBean<Product> findByDown(Integer currPage) throws SQLException;

	PageBean<Product> findByName(String name, Integer currPage) throws SQLException;


}
