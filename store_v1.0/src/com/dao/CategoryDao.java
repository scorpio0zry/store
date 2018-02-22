package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws SQLException;

	Category findByCid(String cid) throws SQLException;

	void save(Category category) throws SQLException;

	void update(Category category) throws SQLException;

	void delete(String cid) throws SQLException;

}
