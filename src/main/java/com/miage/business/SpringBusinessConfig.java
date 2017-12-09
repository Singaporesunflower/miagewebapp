package com.miage.business;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.miage.business.model.Role;
import com.miage.business.repository.CategoryRepository;
import com.miage.business.repository.RoleRepository;
 
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.miage.business")
@EnableJpaRepositories(basePackages = {"com.miage.business.repository"})
@PropertySource(value = {"classpath:persistence.properties"})
@EnableTransactionManagement
public class SpringBusinessConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBusinessConfig.class);
	
    @Autowired
    private Environment environment;
    
    @Bean
	public CommandLineRunner demo(RoleRepository roleRepository, CategoryRepository categoryRepository) {
		return (args) -> {
			// roles
			Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
			Role roleUser = roleRepository.findByName("ROLE_USER");
			if (roleAdmin == null) {
				roleAdmin = new Role("ROLE_ADMIN");
				roleRepository.save(roleAdmin);
				LOGGER.info("ROLE_ADMIN created");
			}
			else {
				LOGGER.info("ROLE_ADMIN found");
			}
			if (roleUser == null) {
				roleUser = new Role("ROLE_USER");
				roleRepository.save(roleUser);
				LOGGER.info("ROLE_USER created");
			}
			else {
				LOGGER.info("ROLE_USER found");
			}
		};
	}
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setPackagesToScan("com.miage.business"); 
        emfb.setDataSource(dataSource());
        emfb.setJpaVendorAdapter(jpaVendorAdapter());
        emfb.setJpaPropertyMap(jpaPropertiesMap()); 
        return emfb;
    }
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//            .setType(EmbeddedDatabaseType.H2)
//            .build();
//    }
    
    @Bean 
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
    
    public Map<String, Object> jpaPropertiesMap() {
    	Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;        
    }
     
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
    
}