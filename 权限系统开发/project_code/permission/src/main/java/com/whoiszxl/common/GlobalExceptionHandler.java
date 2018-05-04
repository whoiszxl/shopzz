package com.whoiszxl.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.whoiszxl.exception.PermissionException;

/**
 * 全局异常处理
 * 
 * @author whoiszxl
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	public static final String DEFAULT_ERROR_VIEW = "error";
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public JsonData defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {

		String url = request.getRequestURL().toString();
		ModelAndView mv;
		String defaultMsg = "system exception!";
		JsonData result;
		if (url.endsWith(".json")) {
			if (e instanceof PermissionException) {
				result = JsonData.fail(e.getMessage());
				mv = new ModelAndView("jsonView", result.toMap());
			} else {
				logger.error("unknown json exception, url:" + url, e);
				result = JsonData.fail(defaultMsg);
				mv = new ModelAndView("jsonView", result.toMap());
			}
		} else if (url.endsWith(".page")) {
			logger.error("unknown page exception, url:" + url, e);
			result = JsonData.fail(defaultMsg);
			mv = new ModelAndView("exception", result.toMap());
		} else {
			logger.error("unknow exception, url:" + url, e);
			result = JsonData.fail(defaultMsg);
			mv = new ModelAndView("jsonView", result.toMap());
		}
		return result;
	}
}
