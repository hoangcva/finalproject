package com.project.ecommerce.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // Tải file: validation.properties
        messageSource.setBasenames("classpath:validation","classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //
        // Access Bootstrap static resource:
        //

        //
        // http://somedomain/SomeContextPath/jquery/jquery.min.css
        //
        registry.addResourceHandler("/jquery/**") //
                .addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.4.1/");

        //
        // http://somedomain/SomeContextPath/popper/popper.min.js
        //
        registry.addResourceHandler("/popper/**") //
                .addResourceLocations("classpath:/META-INF/resources/webjars/popper.js/1.14.3/umd/");

        // http://somedomain/SomeContextPath/bootstrap/css/bootstrap.min.css
        // http://somedomain/SomeContextPath/bootstrap/js/bootstrap.min.js
        //
        registry.addResourceHandler("/bootstrap/**") //
                .addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.3.1/");

        registry.addResourceHandler("/jquery-ui/**") //
                .addResourceLocations("classpath:/META-INF/resources/webjars/jquery-ui/1.12.1/");

        registry.addResourceHandler("/mdb/**") //
                .addResourceLocations("classpath:/resources/mdb/");
    }
}
