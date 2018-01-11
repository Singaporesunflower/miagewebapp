package com.miage.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.miage.rest")
@EnableWebMvc // <mvc:annotation-driven /> et permet utilisation @Controller
public class SpringRestConfig extends WebMvcConfigurerAdapter {

}