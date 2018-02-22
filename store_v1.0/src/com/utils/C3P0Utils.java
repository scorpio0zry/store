package com.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class C3P0Utils {
	private C3P0Utils(){}
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = cpds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("数据库连接失败！");
		}
		return conn;
	}
	
	public static DataSource getDataSource(){
		return cpds;
	}
	
	public static void close(Connection conn,Statement state, ResultSet rs){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}
		
		if(state != null){
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			state = null;
		}
		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}
		
	}
	
	public static void close(Connection conn,Statement state){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}
		
		if(state != null){
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			state = null;
		}
	
	}
}
