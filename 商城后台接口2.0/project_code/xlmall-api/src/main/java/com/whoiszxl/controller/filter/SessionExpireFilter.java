package com.whoiszxl.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;
import com.whoiszxl.utils.CookieUtil;
import com.whoiszxl.utils.JsonUtil;
import com.whoiszxl.utils.RedisPoolUtil;

/**
 * session续命过滤器
 * @author whoiszxl
 *
 */
public class SessionExpireFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(SessionExpireFilter.class);
	
	/**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();
    
    @Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("UserFilter.init()");
		Pattern p = Pattern.compile("/user/login");
		Pattern p2 = Pattern.compile("/manage/user/login");
		patterns.add(p);
		patterns.add(p2);
	}
    
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		// 校验uri，排除不需要过滤的
		String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
		if (isInclude(url)) {
			chain.doFilter(request, response);
			return;
		}
		
		//HttpSession session = httpServletRequest.getSession();
		//User user = (User) session.getAttribute(Const.CURRENT_USER);
		
		String loginToken = CookieUtil.readLoginToken(httpServletRequest);
		User user = null;
		if(StringUtils.isNotEmpty(loginToken)) {
			String userJsonStr = RedisPoolUtil.get(loginToken);
			user = JsonUtil.string2Obj(loginToken, User.class);
		}
		
		ServerResponse<String> errorObj = null;
		if(user != null) {
			//如果user不为空，则重置session的时间，即调用expire命令
			RedisPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
		}
		chain.doFilter(request, response);	
	}
	
	/**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
	public void destroy() {
	}
	

}
