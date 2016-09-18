package spittr.db;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DataSourceConfig {
	
	@Bean
	  public DataSource dataSource() {
	    EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
	    edb.setType(EmbeddedDatabaseType.H2);
	    edb.addScript("classpath:scripts/db/schema.sql");
		edb.addScript("classpath:scripts/db/test-data.sql");
	    EmbeddedDatabase embeddedDatabase = edb.build();
	    return embeddedDatabase;
	  }
	
}
