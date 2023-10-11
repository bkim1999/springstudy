package com.gdu.app11.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;

@PropertySource(value="classpath:application.properties")
@EnableAspectJAutoProxy
@Configuration
public class AppConfig {
  
  @Autowired
  private Environment env;
  
  @Bean
  public DataSource driverManagerDataSource() {
    DriverManagerDataSource d = new DriverManagerDataSource();
    d.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
    d.setUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
    d.setUsername(env.getProperty("spring.datasource.hikari.username"));
    d.setPassword(env.getProperty("spring.datasource.hikari.password"));
    return d;
  }
  
  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(driverManagerDataSource());
  }
  
  @Bean
  public TransactionManager transactionManager() {
    return new DataSourceTransactionManager(driverManagerDataSource());
  }
  
  
}
