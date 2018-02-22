/**
 * CacheUtils
 */
package com.utils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author scorpio0zry
 *
 */
public class CacheUtils {
	private static CacheManager manager;
//	private String
	
	static{
		manager = CacheManager.create(CacheUtils.class.getClassLoader().getResourceAsStream("ehcache.xml"));
	}
	
	public static boolean inCache(String cacheName,String key){
		boolean inCache = false;
		try{
			Cache  cache = manager.getCache(cacheName);
			Element  element = cache.get(key);
			if(element!=null){
				inCache  = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return inCache;
	}	
	
	public static Object get(String cacheName,String key){
		try{
			Cache  cache = manager.getCache(cacheName);
			Element  element = cache.get(key);
			if(element!=null){
				return element.getObjectValue();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static <T> void save(String cacheName,String key,T value){
		try{
			Cache  cache = manager.getCache(cacheName);
			Element  element = new Element(key, value);
			cache.put(element);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void delete(String cacheName,String key){
		try{
			Cache  cache = manager.getCache(cacheName);
			cache.remove(key);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
