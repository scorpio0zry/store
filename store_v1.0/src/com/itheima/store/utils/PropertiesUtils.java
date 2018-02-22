/**
 * 
 */
package com.itheima.store.utils;

import java.util.Properties;

/**
 * @author scorpio0zry
 *
 */
public class PropertiesUtils {
	public static String getProperties(String file,String property){
		try{
			Properties prop = new Properties();
			prop.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(file));
			String value = prop.getProperty(property);
			return value;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static String getProperties(String property){
		try{
			Properties prop = new Properties();
			prop.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("common.properties"));
			String value = prop.getProperty(property);
			return value;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}