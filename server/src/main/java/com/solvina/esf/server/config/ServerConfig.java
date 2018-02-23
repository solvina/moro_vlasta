package com.solvina.esf.server.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * User: Vlastimil
 * Date: 2/23/18
 * Time: 10:06 AM
 */
@Configuration
@ComponentScan(basePackages = "com.solvina.esf.server")
@EnableTransactionManagement
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
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
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
        properties.setProperty("hbm2ddl.auto","create-drop");
        //just because I can
        properties.setProperty("show_sql","true");
        properties.setProperty("format_sql","true");
        return properties;
    }
}
