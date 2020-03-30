package com.whoiszxl.seata.filter;

import com.alibaba.fescar.core.context.RootContext;
import com.whoiszxl.seata.config.FescarAutoConfiguration;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: whoiszxl
 * @create: 2020-03-30
 **/
public class FescarRMRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FescarRMRequestFilter.class);

    /**
     * 给每次线程请求绑定一个XID
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currentXID = request.getHeader(FescarAutoConfiguration.FESCAR_XID);
        if (!StringUtils.isEmpty(currentXID)) {
            RootContext.bind(currentXID);
            LOGGER.info("当前线程绑定的XID :" + currentXID);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            String unbindXID = RootContext.unbind();
            if (unbindXID != null) {
                LOGGER.info("当前线程从指定XID中解绑 XID :" + unbindXID);
                if (!currentXID.equals(unbindXID)) {
                    LOGGER.info("当前线程的XID发生变更");
                }
            }
            if (currentXID != null) {
                LOGGER.info("当前线程的XID发生变更");
            }
        }
    }
}
