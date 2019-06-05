package name.voyachek.demos.nativejpm;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc.SslMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Factory
public class DataSourceFactory {

    private final static Logger LOG = LoggerFactory.getLogger(DataSourceFactory.class);

    @Bean
    @Singleton
    public DataSource dataSource() {
        LOG.debug("Create dataSource");

        final PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl("jdbc:postgresql://localhost/demo_nativem");
        ds.setUser("test");
        ds.setPassword("test");
        ds.setSslMode(SslMode.DISABLE.value);

        return ds;
    }

}
