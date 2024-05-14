package net.pmolinav.bookings.functional;


import net.pmolinav.bookings.security.WebSecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *  Docker Environment is needed to run functional tests.
 **/
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

    protected void givenSomePreviouslyStoredDataWithIds(long id, long id2, boolean createActivities, boolean createUsers, boolean createBookings) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            try (Statement statement = connection.createStatement()) {

                if (createActivities) {
                    String insertActivityQuery = "INSERT INTO activities (activity_id, type, name, description, price, creation_date, modification_date) " +
                            "VALUES (" + id + ", 'FOOTBALL', 'Football', 'Football activity', 25, '2024-01-01 08:00:00', '2024-01-01 08:00:00');";
                    statement.executeUpdate(insertActivityQuery);

                    String insertActivityQuery2 = "INSERT INTO activities (activity_id, type, name, description, price, creation_date, modification_date) " +
                            "VALUES (" + id2 + ", 'TENNIS', 'Tennis', 'Tennis Activity', 15.50, '2023-01-02 10:00:00', '2024-01-02 10:00:00');";
                    statement.executeUpdate(insertActivityQuery2);
                }
                if (createUsers) {
                    String insertUserQuery = "INSERT INTO users (user_id, username, password, name, email, role, creation_date, modification_date) " +
                            "VALUES (" + id + ", 'someUser', '" + WebSecurityConfig.passwordEncoder().encode("somePassword") + "', 'John Doe', 'john@example.com', 'ADMIN', '2024-02-14 10:00:00', NULL);";
                    statement.executeUpdate(insertUserQuery);

                    String insertUserQuery2 = "INSERT INTO users (user_id, username, password, name, email, role, creation_date, modification_date) " +
                            "VALUES (" + id2 + ", 'otherUser', '" + WebSecurityConfig.passwordEncoder().encode("somePassword") + "', 'Jane Smith', 'jane@example.com', 'ADMIN', '2024-02-14 10:30:00', '2024-02-14 11:15:00');";
                    statement.executeUpdate(insertUserQuery2);
                }
                if (createBookings) {
                    String insertBookingQuery = "INSERT INTO bookings (booking_id, user_id, activity_id, start_time, end_time, status, creation_date, modification_date) " +
                            "VALUES (" + id + ", " + id + ", " + id + ", '2024-02-14 09:00:00', '2024-02-14 11:00:00', 'OPEN', '2024-02-14 08:00:00', NULL);";
                    statement.executeUpdate(insertBookingQuery);

                    String insertBookingQuery2 = "INSERT INTO bookings (booking_id, user_id, activity_id, start_time, end_time, status, creation_date, modification_date) " +
                            "VALUES (" + id2 + ", " + id2 + ", " + id2 + ", '2024-02-15 14:00:00', '2024-02-15 16:00:00', 'OPEN', '2024-02-15 13:00:00', '2024-02-15 13:30:00');";
                    statement.executeUpdate(insertBookingQuery2);
                }
            }
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}

