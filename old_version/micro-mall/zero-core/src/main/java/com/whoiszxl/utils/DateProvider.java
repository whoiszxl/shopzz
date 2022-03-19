package com.whoiszxl.utils;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 日期服务
 */
public interface DateProvider {

	/**
	 * 获取当前时间
	 * @return
	 */
	LocalDateTime now();

	Date dateNow();


	/**
	 * Date 转 LocalDateTime
	 * @param date
	 * @return
	 */
	LocalDateTime dateToLocalDateTime(Date date);


	/**
	 * 时间戳 转 LocalDateTime
	 * @param timestamp
	 * @return
	 */
	LocalDateTime longToLocalDateTime(Long timestamp);
	
}
