package net.pmolinav.bookings.functional;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles("test")
public abstract class AbstractContainerBaseTest {
    @Value("${database.port}")
    private int databasePort;
    private final static int DB_PORT = 5432;
    static final PostgreSQLContainer<?> postgresContainer;

    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:latest")
                .withExposedPorts(DB_PORT)
                .withDatabaseName("bookings")
                .withUsername("postgres")
                .withPassword("mysecretpassword");
        postgresContainer.start();

        System.setProperty("database.port", String.valueOf(postgresContainer.getMappedPort(DB_PORT)));

        System.out.println("Connection started for database: " + postgresContainer.getDatabaseName() +
                " and mapped port: " + postgresContainer.getMappedPort(DB_PORT));

    }

}

