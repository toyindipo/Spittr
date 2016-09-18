package spittr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
public class MethodSecurityConfig
       extends GlobalMethodSecurityConfiguration {
	
//	@Bean
//	public HttpSessionSecurityContextRepository securityContextRepository() {
//		HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();
//		repository.setAllowSessionCreation(false);
//		return repository;
//	}
//	
//	@Bean
//	public SecurityContextPersistenceFilter persitenceFilter
//	    (HttpSessionSecurityContextRepository securityContextRepository) {
//		SecurityContextPersistenceFilter filter = 
//				new SecurityContextPersistenceFilter(securityContextRepository);
//		return filter;
//	}
}