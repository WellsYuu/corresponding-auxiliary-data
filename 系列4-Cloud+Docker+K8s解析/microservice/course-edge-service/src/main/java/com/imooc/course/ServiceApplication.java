package com.imooc.course;

import com.imooc.course.filter.CourseFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2017/10/28.
 */
@SpringBootApplication
public class ServiceApplication {

    public static void main(String args[]) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(CourseFilter courseFilter ) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(courseFilter);

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }
}
