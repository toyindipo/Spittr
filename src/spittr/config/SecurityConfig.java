package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.
                            configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import spittr.security.SpitterUserService;

@Configuration
@EnableWebSecurity
 public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SpitterUserService spitterUserService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
	    .userDetailsService(spitterUserService);
	}
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http	      
	      .rememberMe()
	        //.tokenRepository(new InMemoryTokenRepositoryImpl())
	        .tokenValiditySeconds(2419200)
	        .key("spittrKey")
	      .and()
	       .httpBasic()
	         .realmName("Spittr")
	      .and()
	      .authorizeRequests()	 
	        .antMatchers("/", "/home", "homepage").permitAll()
	        .antMatchers("/spitter/me").authenticated()
	        .antMatchers(HttpMethod.POST, "/spittles").authenticated()
	        .antMatchers(HttpMethod.GET, "/spitters/register").authenticated()
	        .antMatchers(HttpMethod.POST, "/spitters/register").access("hasRole('ROLE_SPITTER')")
	        .anyRequest().permitAll()
	        .and()
	        .requiresChannel()
            .antMatchers("/spitter/register").requiresSecure()
            .and()
	        .formLogin()	         
	          .loginPage("/login")
	            .defaultSuccessUrl("/")
	            .failureUrl("/login?error=true");
//	          .and()
//              .logout()
//	  			.logoutUrl("/logout")
//	  			.logoutSuccessUrl("/")  			
//	  			.invalidateHttpSession(true)  			
//	  			.logoutSuccessUrl("/");
	    	http.csrf()
	    	.csrfTokenRepository(csrfTokenRepository());
	      
	  }
	
	private CsrfTokenRepository csrfTokenRepository() { 
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
	    repository.setSessionAttributeName("_csrf");
	    return repository; 
	}
}