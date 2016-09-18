package spittr.alert;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import spittr.misc.Spitter;

@Component
public class SpitterAlertHandler {
	
	@JmsListener(destination = "spitter.alert.queue")
	public void handleSpitterAlert(Spitter spitter) {
		System.out.println(spitter.getEmail());
	}
}
