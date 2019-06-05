package name.voyachek.demos.nativemcp;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.apache.cayenne.configuration.Constants;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.di.Key;
import org.apache.cayenne.resource.ResourceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.sql.DataSource;

@Factory
public class CayenneRuntimeFactory {

    private final DataSource dataSource;

    private final static Logger LOG = LoggerFactory.getLogger(CayenneRuntimeFactory.class);

    public CayenneRuntimeFactory(DataSource dataSource) {
        LOG.debug("Create CayenneRuntimeFactory, dataSource: {}",
                dataSource);

        this.dataSource = dataSource;
    }

    @Bean
    @Singleton
    public ServerRuntime cayenneRuntime() {
        LOG.debug("Create cayenneRuntime");

        return ServerRuntime.builder()
                .dataSource(dataSource)
                .addConfig("db/cayenne-test.xml")
                .addModule(binder -> {
                    binder.bind(ResourceLocator.class).to(ClassLoaderResourceLocatorFix.class);
                    binder.bind(Key.get(ResourceLocator.class, Constants.SERVER_RESOURCE_LOCATOR)).to(ClassLoaderResourceLocatorFix.class);
                })
                .build();
    }

}
