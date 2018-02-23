package com.solvina.esf.server.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 10:06 AM
 */
@Configuration
@ComponentScan(basePackages = "com.solvina.esf.server")
@EnableJpaRepositories(basePackages = "com.solvina.esf.server.dao")
public class ServerConfig {
    private static Logger log = LogManager.getLogger(ServerConfig.class);

    @Value("${db.url:jdbc:postgresql://localhost/moro}")
    private String url;
    @Value("${db.username:vlasta}")
    private String username;
    @Value("${db.password:vlasta}")
    private String password;

    @Bean
      public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
         LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
         em.setDataSource(dataSource());
         em.setPackagesToScan(new String[] { "com.solvina.esf.server.model" });

         JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
         em.setJpaVendorAdapter(vendorAdapter);
         em.setJpaProperties(hibernateProperties());

         return em;
      }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(emf);

       return transactionManager;
    }

    private DataSource dataSource() {
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(10);
        ds.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        ds.addDataSourceProperty("url", url);
        ds.addDataSourceProperty("user", username);
        ds.addDataSourceProperty("password", password);
        return ds;
    }


    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto","create");
        //just because I can
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.format_sql","true");
        return properties;
    }
}
