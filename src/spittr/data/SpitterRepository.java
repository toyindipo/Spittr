package spittr.data;

import java.util.List;

import spittr.misc.Spitter;

public interface SpitterRepository {
	
	Spitter save(Spitter spitter);
	  
	Spitter findByUsername(String username);
	
	List<Spitter> findSpitters(long max, int couunt);

}
