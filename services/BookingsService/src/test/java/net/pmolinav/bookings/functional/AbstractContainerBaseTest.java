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
    static String jdbcUrl;
    static String username;
    static String password;

    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:latest")
                .withExposedPorts(DB_PORT)
                .withDatabaseName("bookings")
                .withUsername("postgres")
                .withPassword("mysecretpassword");

        postgresContainer.start();

        jdbcUrl = postgresContainer.getJdbcUrl();
        username = postgresContainer.getUsername();
        password = postgresContainer.getPassword();

        System.setProperty("database.port", String.valueOf(postgresContainer.getMappedPort(DB_PORT)));

        System.out.println("Connection started for database: " + postgresContainer.getDatabaseName() +
                " and mapped port: " + postgresContainer.getMappedPort(DB_PORT));

    }

}

