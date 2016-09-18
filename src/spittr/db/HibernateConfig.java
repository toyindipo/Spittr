package spittr.db;

//import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
	
	@Bean	
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	  LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
	  sfb.setDataSource(dataSource);
	  sfb.setPackagesToScan(new String[] { "spittr.misc" });
	  Properties props = new Properties();
	  props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
	  sfb.setHibernateProperties(props);
	  return sfb;
	}
	
	
//	@Bean
//	  public SessionFactory sessionFactoryBean() {
//	    try {
//	      LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
//	      lsfb.setDataSource(dataSource());
//	      lsfb.setPackagesToScan("spittr.misc");
//	      Properties props = new Properties();
//	      props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
//	      lsfb.setHibernateProperties(props);
//	      lsfb.afterPropertiesSet();
//	      SessionFactory object = lsfb.getObject();
//	      return object;
//	    } catch (IOException e) {
//	      return null;
//	    }
//	  }	
	
	@Bean
	public BeanPostProcessor persistenceTranslation() {
	  return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager(SessionFactory sessionFactory) {
	    System.out.println(sessionFactory);
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory);
	    return transactionManager;
	 }

}
