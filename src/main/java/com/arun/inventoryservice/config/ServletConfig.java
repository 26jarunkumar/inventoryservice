package com.arun.inventoryservice.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<CamelHttpTransportServlet> camelServletRegistrationBean() {
        ServletRegistrationBean<CamelHttpTransportServlet> registration = new ServletRegistrationBean<>(
            new CamelHttpTransportServlet(), "/camel/*");
        registration.setName("CamelServlet");//camelServletRegistrationBean
        return registration;
    }
}