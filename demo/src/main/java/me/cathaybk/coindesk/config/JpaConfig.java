package me.cathaybk.coindesk.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaConfig {
	
	@Autowired
	private DataSource dataSource;
	
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}	
	
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		OpenJpaVendorAdapter openJpaVendorAdapter = new OpenJpaVendorAdapter();
		openJpaVendorAdapter.setShowSql(true);
		openJpaVendorAdapter.setGenerateDdl(true);
		openJpaVendorAdapter.setDatabase(Database.H2);		
		return openJpaVendorAdapter;
	}

	@Bean
	public Properties openJpaProperties() {
		Properties openJpaProperties = new Properties();
		return openJpaProperties;
	}	


	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean =
		new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("me.cathaybk.coindesk.entity");
		factoryBean.setDataSource(dataSource);		
		factoryBean.setJpaProperties(openJpaProperties());
		//factoryBean.setPersistenceUnitName("CurrencySystem");
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.afterPropertiesSet();
		return factoryBean.getNativeEntityManagerFactory();
	}
}
