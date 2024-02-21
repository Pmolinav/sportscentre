package net.pmolinav.bookings.functional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

    @BeforeEach
    public void givenEmptyTablesBeforeTests() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            try (Statement statement = connection.createStatement()) {

                String deleteBookingsQuery = "DELETE FROM bookings;";
                statement.executeUpdate(deleteBookingsQuery);

                String deleteActivitiesQuery = "DELETE FROM activities;";
                statement.executeUpdate(deleteActivitiesQuery);

                String deleteUsersQuery = "DELETE FROM users;";
                statement.executeUpdate(deleteUsersQuery);
            }
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}

