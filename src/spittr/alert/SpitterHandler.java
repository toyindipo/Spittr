package spittr.alert;

import org.springframework.stereotype.Component;

import spittr.misc.Spitter;

@Component
public class SpitterHandler {
	
	public void handleSpitterAlert(Spitter spitter) {
		System.out.println(spitter.getUsername());
	}
}
