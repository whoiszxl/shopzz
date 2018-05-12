package com.whoiszxl.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author whoiszxl
 *
 */
public class CookieUtil {
	
	private static Logger log = LoggerFactory.getLogger(CookieUtil.class);
	
	private final static String COOKIE_DOMAIN = PropertiesUtil.getProperty("cookie.domain");
	private final static String COOKIE_NAME = PropertiesUtil.getProperty("cookie.name");
	
	/**
	 * 写入登录的token到cookie中
	 * @param response
	 * @param token
	 */
	public static void writeLoginToken(HttpServletResponse response, String token) {
		Cookie cookie = new Cookie(COOKIE_NAME, token);
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setPath("/");
		cookie.setHttpOnly(true);//设置仅允许http访问，不允许脚本访问
		/**
		 * 单位是秒，如果maxAge不设置，cookie不会写入硬盘，只会存入内存在当前页面有效
		 * -1 ：永久有效
		 */
		cookie.setMaxAge(60*60*24*30);
		log.info("write cookieName:{},cookieValue:{} success", cookie.getName(), cookie.getValue());
		response.addCookie(cookie);
	}
	
	/**
	 * 从cookie中获取登录token
	 * @param request
	 * @return
	 */
	public static String readLoginToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if(StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
					log.info("read cookieName:{},cookieValue:{} success", cookie.getName(), cookie.getValue());
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * 删除登录token
	 * @param request
	 * @param response
	 */
	public static void deleteLoginToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
				cookie.setDomain(COOKIE_DOMAIN);
				cookie.setPath("/");
				cookie.setMaxAge(0);//有效期设置成0为删除cookie
				log.info("delete cookieName:{} success", cookie.getName());
				response.addCookie(cookie);
				return;
			}
		}
	}
	
}
