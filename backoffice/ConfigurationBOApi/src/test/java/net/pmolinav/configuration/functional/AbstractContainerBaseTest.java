package net.pmolinav.configuration.functional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@ActiveProfiles("test")
public abstract class AbstractContainerBaseTest {
    static String jdbcUrl;
    static String username;
    static String password;

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

