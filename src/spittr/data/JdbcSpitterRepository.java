package spittr.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import spittr.misc.Spitter;

//@Repository
public class JdbcSpitterRepository implements SpitterRepository {
	private JdbcTemplate jdbcTemplate;
	
	public JdbcSpitterRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Spitter save(Spitter spitter) {
		if(spitter.getId() == null) {
			return createNewSpitter(spitter);
		} else {
			return updateSpitter(spitter);
		}
	}
	
	public Spitter findByUsername(String username) {
		return jdbcTemplate.queryForObject(SELECT_SPITTER + " where username=?", 
				new SpitterRowMapper(), username);
	}
	
	public List<Spitter> findSpitters(long max, int count) {
		return jdbcTemplate.query(SELECT_SPITTER + " where id<=? LIMIT ?", 
				new SpitterRowMapper(), max, count);		
	}
	
	private Spitter updateSpitter(Spitter spitter) {
		jdbcTemplate.update(UPDATE_SPITTER, spitter.getUsername(), 
				spitter.getPassword(), spitter.getFirstName(), spitter.getLastName(), 
				spitter.getEmail(), spitter.getId());
		
		return spitter;
	}
	
	private Spitter createNewSpitter(Spitter spitter) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("Spitter");
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", spitter.getUsername());
		args.put("password", spitter.getPassword());
		args.put("firstName", spitter.getFirstName());
		args.put("lastName", spitter.getLastName());
		args.put("email", spitter.getEmail());
		
		long spitterId = jdbcInsert.executeAndReturnKey(args).longValue();
		spitter.setId(spitterId);
		return spitter;
	}
	
	/**
	 * Inserts a spitter using a simple JdbcTemplate update() call.
	 * Does not return the ID of the newly created Spitter.
	 * @param spitter a Spitter to insert into the database
	 */
	@SuppressWarnings("unused")
	private void insertSpitter(Spitter spitter) {
		jdbcTemplate.update(INSERT_SPITTER, 
			spitter.getUsername(),
			spitter.getPassword(),
			spitter.getFirstName(),
			spitter.getLastName(),
			spitter.getEmail());
	}
	
	private static final class SpitterRowMapper implements RowMapper<Spitter> {		
		public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Spitter(rs.getLong("id"), rs.getString("username"), 
					rs.getString("password"), rs.getString("firstName"), 
					rs.getString("lastName"), rs.getString("email"));
		}		
	}
	
	private static final String INSERT_SPITTER = "insert into Spitter (username, password, firstName, lastName, email) values (?, ?, ?, ?, ?)";

	private static final String SELECT_SPITTER = "select id, username, password, firstName, lastName, email from Spitter";
	
	private static final String UPDATE_SPITTER = "update Spitter set username=?, password=?, firstName=?, lastName=?, email=?, updateByEmail=? where id=?";
}
