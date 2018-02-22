package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.domain.Product;

public interface ProductDao {

	List<Product> findByNew() throws SQLException;

	List<Product> findByHot() throws SQLException;

	int findCountByCid(String cid) throws SQLException;

	List<Product> findPageByCid(String cid, int begin, int pageEach) throws SQLException;

	Product findByPid(String pid) throws SQLException;

	String findCid(String pid) throws SQLException;

	Integer findCount() throws SQLException;

	List<Product> findPage(int begin, Integer pageEach) throws SQLException;

	void save(Product product) throws SQLException;

	void update(Product product) throws SQLException;

	Integer findCountByDown() throws SQLException;

	List<Product> findPageDown(int begin, Integer pageEach) throws SQLException;

	int findCountByName(String name) throws SQLException;

	List<Product> findPageByName(String name, int begin, int pageEach) throws SQLException;


}
