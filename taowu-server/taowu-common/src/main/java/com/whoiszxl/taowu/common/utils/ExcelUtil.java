package com.whoiszxl.taowu.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.whoiszxl.taowu.common.exception.ExceptionCatcher;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Excel工具
 * @author whoiszxl
 */
@Slf4j
public class ExcelUtil {

    /**
     * excel导出
     * @param list 需要导出的数据
     * @param fileName 文件名
     * @param clazz 导出数据的数据类型
     * @param response http response
     * @param <E> 数据类型
     */
    public static <E> void export(List<E> list,
                                  String fileName,
                                  Class<E> clazz,
                                  HttpServletResponse response) {

        try {
            fileName = String.format("%s_%s.xlsx", fileName, DateUtil.now());
            fileName = URLUtil.encode(fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");

            EasyExcel.write(response.getOutputStream(), clazz)
                    .autoCloseStream(false)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerConverter(new LongStringConverter())
                    .registerConverter(new LocalDateTimeStringConverter())
                    .sheet()
                    .doWrite(list);
        }catch (Exception e) {
            log.error("ExcelUtil|表格导出异常|{}", fileName, e);
            ExceptionCatcher.catchServiceEx("表格导出异常");
        }

    }
}
