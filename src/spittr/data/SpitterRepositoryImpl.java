package spittr.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import spittr.misc.Spitter;

@Repository
public class SpitterRepositoryImpl implements SpitterRepository {
	private List<Spitter> spitters = 
			new ArrayList<Spitter>(Arrays.asList(
					new Spitter[] { new Spitter(0L, "toyindipo", "password", "Toyin", "Dipo", "toyindipo@mail.com") }));
	private long spitterId = 1;
	
	public Spitter save(Spitter spitter) {
		spitter.setId(spitterId);
		spitters.add(spitter);
		incSpitterId();
		return spitter;
	}
	
	public Spitter findByUsername(String username) {
		Spitter result = null;
		
		for(Spitter spitter: spitters) {
			if(spitter.getUsername().equals(username)) {
				result = spitter;
				break;
			}
		}
		
		return result;
	}
	
	private void incSpitterId() {
		spitterId += 1;
	}
	
	public List<Spitter> findSpitters(long max, int count) {
		List<Spitter> resultSpitters = new ArrayList<Spitter>();
		for(Spitter spitter: spitters) {
			if(spitter.getId() <= max) {
				resultSpitters.add(spitter);
				if(resultSpitters.size() == count)
					break;
			}
		}
		return resultSpitters;
	}
}
