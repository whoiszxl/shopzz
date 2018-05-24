package com.whoiszxl.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.whoiszxl.entity.User;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.MD5Util;
import com.whoiszxl.utils.PropertiesUtil;
import com.whoiszxl.utils.RedisShardedPoolUtil;

/**
 * jwt操作用工具类
 * @author whoiszxl
 *
 */
public class JWTUtil {

	private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);
	
	private static final long EXPIRE_TIME = Integer.parseInt(PropertiesUtil.getProperty("jwt.token.expire.time","60000"));
	
	/**
	 * 生成jwt签名
	 * @param username 用户名
	 * @param password 用户密码
	 * @param id 用户的id
	 * @return 加密后的token
	 */
	public static String sign(String username, String password, int id) {
		logger.error("sign：：：username：{}，password：{}，id：{}，",  username, password, id);
		try {
			String md5Password = MD5Util.MD5EncodeUtf8(password);
			Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(md5Password);
			
			//附带username信息
			return JWT.create()
					.withClaim("username", username)
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			logger.error("生成jwt签名不成功了", e);
			return null;
		}
	}
	
	
	/**
	 * 校验token是否正确
	 * @param token 密钥
	 * @param username 用户的账号
	 * @param password 用户的密码
	 * @param id 用户的id
	 * @return 是否正确哦
	 */
	public static boolean verify(String token, String username, String password, int id) {
		logger.error("verify：：：token：{}，username：{}，password：{}，id：{}，", token, username, password, id);
		try {
			Algorithm algorithm = Algorithm.HMAC256(password);
			JWTVerifier verifier = JWT.require(algorithm)
					.withClaim("username", username)
					.build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception e) {
			logger.error("校验jwt签名不成功了", e);
			return false;
		}
	}
	
	/**
	 * 获取token中的信息，无需password解密也行
	 * @param token 密钥
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (Exception e) {
			logger.error("获取jwt用户名不成功了", e);
			return null;
		}
	}
	
	
	/**
	 * 获取token中的信息，无需password解密也行
	 * @param token 密钥
	 * @return token中包含的用户名
	 */
	public static Integer getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("id").asInt();
		} catch (Exception e) {
			logger.error("获取jwt id不成功了", e);
			return null;
		}
	}
	
}
