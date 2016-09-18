package spittr.data;

import java.util.List;
import spittr.misc.Spittle;

public interface SpittleRepository {
	
	List<Spittle> findSpittles(long max, int couunt);
	
	Spittle findOne(long id);
	
	Spittle save(Spittle spittle);
	
}
