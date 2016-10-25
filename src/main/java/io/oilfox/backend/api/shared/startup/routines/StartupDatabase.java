package io.oilfox.backend.api.shared.startup.routines;

import com.google.inject.Injector;
import com.zaxxer.hikari.HikariDataSource;
import io.oilfox.backend.db.entities.DbSession;
import io.oilfox.backend.db.db.SessionProvider;
import io.oilfox.backend.api.shared.exception.OilfoxException;
import io.oilfox.backend.api.shared.helpers.ReflectionHelper;
import io.oilfox.backend.api.shared.properties.ApplicationPropertiesLoader;
import io.oilfox.backend.api.shared.properties.DatabaseProperties;
import io.oilfox.backend.api.shared.startup.common.StartupRoutine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Entity;

@Singleton
public class StartupDatabase extends StartupRoutine implements SessionProvider {

    private static Logger logger = LoggerFactory.getLogger(StartupDatabase.class);

    @Inject
    private DatabaseProperties databaseProperties;

    @Inject
    private ApplicationPropertiesLoader applicationPropertiesLoader;

    @Inject
    private Injector injector;

    private SessionFactory sessionFactory;

    private HikariDataSource dataSource;

    private String createConnectionString() {
        return String.format("jdbc:postgresql://%s:%d/%s",
                databaseProperties.getHostname(),
                databaseProperties.getPort(),
                databaseProperties.getDatabase());
    }

    @Override
    public void run() throws OilfoxException {

        String connectionString = createConnectionString();

        Configuration configuration = new Configuration()
                .configure()
                .setProperty("hibernate.connection.url", connectionString)
                .setProperty("hibernate.connection.username", databaseProperties.getUsername())
                .setProperty("hibernate.connection.password", databaseProperties.getPassword());

        // add all mappings via reflection
        String entityNamespace = DbSession.class.getPackage().getName();
        Reflections reflections = new Reflections(entityNamespace);

        for(Class<?> clazz : reflections.getTypesAnnotatedWith(Entity.class)) {
            configuration.addAnnotatedClass(clazz);
        }

        // add settings from "application.config". These will overwrite the values from "hibernate.cfg.xml"
        java.util.Properties hibernateProperties = applicationPropertiesLoader.getProperties("hibernate.");

        for(String name : hibernateProperties.stringPropertyNames()) {
            logger.debug("Hibernate: \"" + name +"\" set to \"" + hibernateProperties.getProperty(name) + "\"");
        }

        configuration.addProperties(hibernateProperties);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        logger.debug(String.format("Connecting to %s with user %s", connectionString, databaseProperties.getUsername()));

        /*
            As of 20160124 a failing construction of the session factory (due to schema inconsistencies) will nonetheless
            open connection from the HikariCP. This leads to problems executing the integration tests. The second test accessing
            the database will fail, because there are still open connections to the old database.

            So for now we close the sessions manually

         */
        JdbcServices services = registry.getService(JdbcServices.class);
        JdbcConnectionAccess connection = services.getBootstrapJdbcConnectionAccess();
        Object connectionProvider = ReflectionHelper.getPrivateField(connection, "connectionProvider");
        dataSource = (HikariDataSource)ReflectionHelper.getPrivateField(connectionProvider, "hds");

        try {
            sessionFactory = configuration.buildSessionFactory(registry);
        }
        catch(Exception e) {

            // close HikariCP explicitly
            dataSource.close();

            throw new OilfoxException("Cannot open session factory", e);
        }

        logger.info(String.format("Successfully connected to %s with user %s", connectionString, databaseProperties.getUsername()));
    }

    @Override
    public void stop() {

        logger.info("Shutting down database session factory");
        closeSessionFactory();
    }

    public synchronized void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }

    public synchronized SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Session open() {
        return sessionFactory.openSession();
    }
}
