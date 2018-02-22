package com.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	public static Object getBean(String id){
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml"));
			Element element = (Element) document.selectSingleNode("//bean[@id='"+id+"']");
			Class clazz = Class.forName(element.attributeValue("class"));
			return clazz.newInstance();			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	//配置文件获取不同连接池的QueryRunner
	public static QueryRunner getQueryRunner(String name){
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml"));
			Element element = (Element) document.selectSingleNode("//bean[@name='"+name+"']");
			QueryRunner qr = null;
			if("c3p0".equalsIgnoreCase(element.attributeValue("name"))){
				qr = new QueryRunner(C3P0Utils.getDataSource());
			}
			return qr;		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}		
	}
	
}
