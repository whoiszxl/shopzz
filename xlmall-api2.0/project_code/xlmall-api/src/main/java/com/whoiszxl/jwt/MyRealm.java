package com.whoiszxl.jwt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.entity.User;

/**
 * 
 * @author whoiszxl
 *
 */
@Service
public class MyRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyRealm.class);
	
	@Autowired
	private UserService jwtUserService;
	
	
	/**
     * 必须重写此方法，不然Shiro会报错
     */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}
	
	/**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取到用户名称喔
		String username = JWTUtil.getUsername(principals.toString());
		//通过用户名获取到用户实体
        User user = jwtUserService.getUser(username);
        //
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole().toString());
        //TODO 操作权限限制
        // Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        // simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
	}

	
	/**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		//获取到token
		String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        //从数据库拿到这个用户咯
        User user = jwtUserService.getUser(username);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        
        //存在的话就直接用jwt去校验是否有效
        if (!JWTUtil.verify(token, username, user.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
	}

}
