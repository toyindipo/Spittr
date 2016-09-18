package spittr.advice;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import spittr.alert.AlertService;
import spittr.misc.Spitter;

@Aspect
public class SpitterAlertAdvice {
	
	private AlertService alertService;
	
	public SpitterAlertAdvice(AlertService alertService) {
		this.alertService = alertService;
	}
	
	@Pointcut("execution (* spittr.data.SpitterRepository.save(spittr.misc.Spitter)) " + 
	         "&& args(spitter) && within(spittr.data.*)")
	  public void spitterSaved(Spitter spitter) {}
	
	@AfterReturning("spitterSaved(spitter)")
	  public void alertSpitterSaved(Spitter spitter) {
		alertService.sendSpitterAlert(spitter);
	}
	
	

}
