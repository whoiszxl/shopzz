package com.whoiszxl.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.whoiszxl.utils.JsonMapper;

public class HttpInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

	private static final String START_TIME = "requestStartTime";

	/**
	 * 预处理回调方法，实现处理器的预处理（如登录检查），第三个参数为响应的处理器
	 * 返回值：true表示继续流程（如调用下一个拦截器或处理器）
	 * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI().toString();
		Map<String, String[]> parameterMap = request.getParameterMap();
		logger.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));
		long start = System.currentTimeMillis();
		request.setAttribute(START_TIME, start);
		return true;
	}

	
	/**
	 * 后处理回调方法，实现处理器的后处理（但在渲染视图之前）;
	 * 此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理;
	 * modelAndView也可能为null。
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		removeThreadLocalInfo();
	}

	/**
	 * 整个请求处理完毕回调方法，即在视图渲染完毕时回调;
	 * 如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理,类似于try-catch-finally中的finally;
	 * 但仅调用处理器执行链中preHandle返回true的拦截器的afterCompletion
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String url = request.getRequestURI().toString();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        logger.info("request completed. url:{}, cost:{}", url, end - start);
        removeThreadLocalInfo();
	}

	public void removeThreadLocalInfo() {
		RequestHolder.remove();
	}
}
