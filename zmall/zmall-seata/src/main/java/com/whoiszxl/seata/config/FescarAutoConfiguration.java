package com.whoiszxl.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fescar.rm.datasource.DataSourceProxy;
import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;
import com.whoiszxl.seata.filter.FescarRMRequestFilter;
import com.whoiszxl.seata.interceptor.FescarRestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
/**
 *  * 创建数据源
 *  * 定义全局事务管理器扫描对象
 *  * 给所有RestTemplate添加头信息防止微服务之间调用问题
 */
public class FescarAutoConfiguration {

    public static final String FESCAR_XID = "fescarXID";

    /***
     * 创建代理数据库
     * @param environment
     * @return
     */
    @Bean
    public DataSource dataSource(Environment environment){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.druid.url"));
        try {
            dataSource.setDriver(DriverManager.getDriver(environment.getProperty("spring.datasource.druid.url")));
        } catch (SQLException e) {
            throw new RuntimeException("can't recognize dataSource Driver");
        }
        dataSource.setUsername(environment.getProperty("spring.datasource.druid.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.druid.password"));
        return new DataSourceProxy(dataSource);
    }

    /***
     * 全局事务扫描器
     * 用来解析带有@GlobalTransactional注解的方法，然后采用AOP的机制控制事务
     * @param environment
     * @return
     */
    @Bean
    public GlobalTransactionScanner globalTransactionScanner(Environment environment){
        String applicationName = environment.getProperty("spring.application.name");
        String groupName = environment.getProperty("fescar.group.name");
        if(applicationName == null){
            return new GlobalTransactionScanner(groupName == null ? "my_test_tx_group" : groupName);
        }else{
            return new GlobalTransactionScanner(applicationName, groupName == null ? "my_test_tx_group" : groupName);
        }
    }

    /***
     * 每次微服务和微服务之间相互调用
     * 要想控制全局事务，每次TM都会请求TC生成一个XID，每次执行下一个事务，也就是调用其他微服务的时候都需要将该XID传递过去
     * 所以我们可以每次请求的时候，都获取头中的XID，并将XID传递到下一个微服务
     * @param restTemplates
     * @return
     */
    @ConditionalOnBean({RestTemplate.class})
    @Bean
    public Object addFescarInterceptor(Collection<RestTemplate> restTemplates){
        restTemplates.stream()
                .forEach(restTemplate -> {
                    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
                    if(interceptors != null){
                        interceptors.add(fescarRestInterceptor());
                    }
                });
        return new Object();
    }

    @Bean
    public FescarRMRequestFilter fescarRMRequestFilter(){
        return new FescarRMRequestFilter();
    }

    @Bean
    public FescarRestInterceptor fescarRestInterceptor(){
        return new FescarRestInterceptor();
    }
}
