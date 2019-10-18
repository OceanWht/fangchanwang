package com.wht.springcloud.fangchanwang.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里数据库连接池配置类
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.druid")
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        //Lists.newArrayList 是guava得工具类，这样就将慢日志得功能加到数据库连接池中
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return druidDataSource;
    }

    @Bean
    public Filter statFilter(){
        //druid监控可以监控sql是否过慢
        StatFilter statFilter = new StatFilter();
        //是否打印慢sql日志
        statFilter.setLogSlowSql(true);
        //执行多长时间得sql才鉴定为慢sql，这里设备为5秒
        statFilter.setSlowSqlMillis(5000);
        //是否将日志合并
        statFilter.setMergeSql(true);

        return statFilter;
    }

    /**
     * 添加监控配置
     * @return
     */
    @Bean
    public ServletRegistrationBean registrationBean(){

        return new ServletRegistrationBean(new StatViewServlet(),"/durid/*");
    }
}
