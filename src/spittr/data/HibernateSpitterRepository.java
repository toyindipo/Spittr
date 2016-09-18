package spittr.data;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spittr.misc.Spitter;

@Repository
@Transactional
@Primary
public class HibernateSpitterRepository implements SpitterRepository {
	private SessionFactory sessionFactory;
	
	@Autowired
	public HibernateSpitterRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session currentSession() {
	    return sessionFactory.getCurrentSession();
	}
	
	public Spitter save(Spitter spitter) {
		Serializable id = currentSession().save(spitter);  //<co id="co_UseCurrentSession"/>
		return new Spitter((Long) id, 
				spitter.getUsername(), 
				spitter.getPassword(), 
				spitter.getLastName(),
				spitter.getFirstName(),
				spitter.getEmail());
	}

	public Spitter findOne(long id) {
		return (Spitter) currentSession().get(Spitter.class, id); 
	}

	public Spitter findByUsername(String username) {
		Spitter spitter =
		 (Spitter) currentSession() 
				.createCriteria(Spitter.class) 
				.add(Restrictions.eq("username", username))
				.list().get(0);
		spitter.addRole("SPITTER");
		return spitter;
	}

	public List<Spitter> findSpitters(long max, int count) {
		
		@SuppressWarnings("unchecked")
		List<Spitter> result = (List<Spitter>) currentSession() 
				.createCriteria(Spitter.class)
				.setMaxResults(count)
				.add(Restrictions.le("id", max))				
				.list();
		return result;
	}

}
