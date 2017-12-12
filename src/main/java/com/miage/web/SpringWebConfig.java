package com.miage.web;

import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.miage.web"}) // <context:component-scan />
@EnableWebMvc // <mvc:annotation-driven /> et permet utilisation @Controller
@PropertySource(value="classpath:applicationcustom.properties")
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	public static final String CHARACTER_ENCODING = "UTF-8";
	public static final String MESSAGES_PATH = "classpath:messages";
	
	@Autowired
	private ListableBeanFactory beanFactory; 
	
	@Value("#{T(java.lang.Boolean).parseBoolean('${thymeleaf.cache}')}")
	private Boolean thymeleafCache;

	// Views
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }
	
	// Resources
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	// Formatters
	@Override
	public void addFormatters(final FormatterRegistry registry) {
		for (Formatter<?> formatter : this.beanFactory.getBeansOfType(Formatter.class).values()) {
			registry.addFormatter(formatter);
		}
	}

	// Internationalization
    @Bean
    // @Scope("singleton")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGES_PATH);
        // if true, the key of the message will be displayed if the key is not
        // found, instead of throwing a NoSuchMessageException
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding(CHARACTER_ENCODING);
        // # -1 : never reload, 0 always reload
        messageSource.setCacheSeconds(0);
        return messageSource;
    }
    // Intercept parameter language in URL
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}
	// Locale is stored in session
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver result = new SessionLocaleResolver();
		result.setLocaleAttributeName("lang");
		result.setDefaultLocale(Locale.FRENCH);
		return result;
	}

	// Exceptions
	@Bean
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

		Properties exceptionMappings = new Properties();
		exceptionMappings.put("com.miage.business.exception.RepositoryException", "error/repository");
		exceptionMappings.put("com.miage.business.exception.ServiceException", "error/service");
		// exceptionMappings.put("java.lang.Exception", "error/error");
		// exceptionMappings.put("java.lang.RuntimeException", "error/error");
		exceptionResolver.setExceptionMappings(exceptionMappings);
		exceptionResolver.setDefaultErrorView("error/error");

		Properties statusCodes = new Properties();
		statusCodes.put("error/custom", "404");
		statusCodes.put("error/error", "500");
		exceptionResolver.setStatusCodes(statusCodes);

		return exceptionResolver;
	}

	// Thymeleaf : TemplateResolver <- TemplateEngine <- ViewResolver
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");
		// in Spring 5 : use instead TemplateMode.HTML
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(thymeleafCache);
		return templateResolver;
	}
	@Bean
	public SpringTemplateEngine templateEngine() {
		// SpringTemplateEngine automatically applies SpringStandardDialect and
		// enables Spring's own MessageSource message resolution mechanisms.
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());

		// To use <sec:> namespace in Thymeleaf
		Set<IDialect> additionalDialects = new HashSet<IDialect>();
	    additionalDialects.add(new SpringSecurityDialect());
		templateEngine.setAdditionalDialects(additionalDialects);
		
		return templateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}
}