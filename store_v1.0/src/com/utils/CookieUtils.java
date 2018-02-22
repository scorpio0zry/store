package com.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
	private CookieUtils() {}
	
	public static String findCookie(Cookie[] cookies, String name){
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(name)){
					return cookie.getValue();					
				}
			}
			return null;
		}else{
			return null;
		}
	}
}
