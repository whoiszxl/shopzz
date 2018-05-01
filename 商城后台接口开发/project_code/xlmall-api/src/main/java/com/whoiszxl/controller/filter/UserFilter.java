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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whoiszxl.common.Const;
import com.whoiszxl.common.ServerResponse;
import com.whoiszxl.entity.User;

/**
 * 
 * @author whoiszxl
 *
 */
//@Component
public class UserFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(UserFilter.class);
	
	/**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();
    
    @Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("UserFilter.init()");
		Pattern p = Pattern.compile("/user/login");
		this.patterns.add(p);
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
		
		HttpSession session = httpServletRequest.getSession();
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		ServerResponse<String> errorObj = null;
		if(user == null) {
			errorObj = ServerResponse.createByErrorMessage("用户未登录,请登录");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			ObjectMapper objectMapper = new ObjectMapper();
			String errorStr = objectMapper.writeValueAsString(errorObj);
			response.getWriter().write(errorStr);
		}else {
			chain.doFilter(request, response);
		}
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

