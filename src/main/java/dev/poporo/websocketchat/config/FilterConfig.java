package dev.poporo.websocketchat.config;

import dev.poporo.websocketchat.common.filter.MdcFilter;
import dev.poporo.websocketchat.common.log.UuidTraceIdGenerator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MdcFilter> mdcFilterRegistration() {
        FilterRegistrationBean<MdcFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setFilter(new MdcFilter(new UuidTraceIdGenerator()));
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
