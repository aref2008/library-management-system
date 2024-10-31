package com.library.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.library.demo.middleware.RequestLoggingFilter;
import com.library.demo.repository.LogRepository;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter(LogRepository logRepository) {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestLoggingFilter(logRepository));
        registrationBean.addUrlPatterns("/api/*"); // Apply only to API routes
        return registrationBean;
    }
}
