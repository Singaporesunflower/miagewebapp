package com.miage;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.miage.business.SpringBusinessConfig;
import com.miage.rest.SpringRestConfig;
import com.miage.web.SpringWebConfig;

@SpringBootApplication
public class Application extends SpringBootServletInitializer { // implements WebApplicationInitializer {

	public static final String MAPPING_WEB = "/*";
	public static final String MAPPING_REST = "/rest/*";
	public static final String CHARACTER_ENCODING = "UTF-8";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
    
	@Override
	public void onStartup(ServletContext container) {
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringBusinessConfig.class);

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's for web context
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(SpringWebConfig.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic webDispatcher = container.addServlet("webDispatcher", new DispatcherServlet(webContext));
		webDispatcher.setLoadOnStartup(1);
		webDispatcher.addMapping(MAPPING_WEB);

		// Create the dispatcher servlet's for rest context
		AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
		restContext.register(SpringRestConfig.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic restDispatcher = container.addServlet("restDispatcher", new DispatcherServlet(restContext));
		restDispatcher.setLoadOnStartup(2);
		restDispatcher.addMapping(MAPPING_REST);

		FilterRegistration charEncodingfilterReg = container.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
		charEncodingfilterReg.setInitParameter("encoding", CHARACTER_ENCODING);
		charEncodingfilterReg.setInitParameter("forceEncoding", "true");
		charEncodingfilterReg.addMappingForUrlPatterns(null, false, MAPPING_WEB);
		charEncodingfilterReg.addMappingForUrlPatterns(null, false, MAPPING_REST);
	}

}