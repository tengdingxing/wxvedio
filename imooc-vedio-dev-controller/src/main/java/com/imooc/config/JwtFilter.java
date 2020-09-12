package com.imooc.config;

import com.google.common.collect.Lists;
import com.imooc.pojo.Audience;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.List;


public class JwtFilter  {

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter((Filter) new JwtFilter());
        //添加需要拦截的url
        List<String> urlPatterns = Lists.newArrayList();
        urlPatterns.add("/**");
        registrationBean.addUrlPatterns(urlPatterns.toArray(new String[urlPatterns.size()]));
        return registrationBean;
    }
}
