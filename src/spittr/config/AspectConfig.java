package spittr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import spittr.advice.SpitterAlertAdvice;
import spittr.alert.AlertService;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
	
	@Bean
	public SpitterAlertAdvice spitterAlertAdvice(AlertService alertService) {
	  return new SpitterAlertAdvice(alertService);	
	}
}
