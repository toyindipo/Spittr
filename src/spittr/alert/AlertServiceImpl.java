package spittr.alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

import spittr.misc.Spitter;

@Component
public class AlertServiceImpl implements AlertService {
	
	private JmsOperations jmsOperations;
	
	@Autowired
	public AlertServiceImpl(JmsOperations jmsOperations) {
		this.jmsOperations = jmsOperations;
	}
	
	@Override
	public void sendSpitterAlert(Spitter spitter) {
		System.out.println(spitter.getUsername());
		jmsOperations.convertAndSend(spitter);
	}

}
