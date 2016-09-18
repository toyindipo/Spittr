package spittr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Repository;

import spittr.misc.Spittle;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {
	private static List<Spittle> spittles;
	
	@Override
	public Spittle save(Spittle spittle) {
		spittles.add(spittle);
		
		return spittle;
	}
	
	public List<Spittle> findSpittles(long max, int count) {
		List<Spittle> resultSpittles = new ArrayList<Spittle>();
		for(Spittle spittle: spittles) {
			if(spittle.getId() <= max) {
				resultSpittles.add(spittle);
				if(resultSpittles.size() == count)
					break;
			}
		}
		return resultSpittles;
	}
	
	static {
		spittles = createSpittleList(10);
		
	}
	
	public Spittle findOne(long id) {
		Spittle result = null;
		
		for(Spittle spittle: spittles) {
			if(spittle.getId() == id) {
				result = spittle;
				break;
			}
		}
		
		return result;
	}
	
	public static List<Spittle> getSpittles() {
		return spittles;
	}
	
	private static List<Spittle> createSpittleList(int count) {
	  List<Spittle> spittles = new ArrayList<Spittle>();	  
	  for (int i=1; i < count + 1; i++) {
	    spittles.add(new Spittle(Spittle.getNextId(), "Spittle " + i, new Date()));
	  }
	  return spittles;
	}
}
