package com.whoiszxl.seata.interceptor;

import com.alibaba.fescar.core.context.RootContext;
import com.whoiszxl.seata.config.FescarAutoConfiguration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;

/**
 *
 */
public class FescarRestInterceptor implements RequestInterceptor, ClientHttpRequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String xid = RootContext.getXID();
        if (!StringUtils.isEmpty(xid)) {
            requestTemplate.header(FescarAutoConfiguration.FESCAR_XID, xid);
        }
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String xid = RootContext.getXID();
        if (!StringUtils.isEmpty(xid)) {
            HttpHeaders headers = request.getHeaders();
            headers.put(FescarAutoConfiguration.FESCAR_XID, Collections.singletonList(xid));
        }
        return execution.execute(request, body);
    }
}
