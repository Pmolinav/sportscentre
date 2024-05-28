package net.pmolinav.database;

import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.bookingslib.model.User;

import java.math.BigDecimal;
import java.sql.*;

public class SportsCentreDatabaseConnector {

    private static final String URL = "jdbc:postgresql://localhost:5432/sportscentre";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mysecretpassword";

    private Connection connection;

    public SportsCentreDatabaseConnector() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unexpected error while trying to connect to database.", e);
        }
    }

    /*** USERS  ***/

    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, password, name, email, role, creation_date, modification_date) "
                + "VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set query params.
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setDate(6, new java.sql.Date(user.getCreationDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(user.getModificationDate().getTime()));

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Unexpected error occurred while trying to insert user.", e);
        } finally {
            connection.close();
        }
    }

    public void deleteUsers() throws SQLException {
        String query = "DELETE FROM user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Unexpected error occurred while trying to delete users.", e);
        } finally {
            connection.close();
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT user_id, username, password, name, email, role, creation_date, modification_date" +
                " FROM users WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set query params.
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Extract data from result set.
            if (resultSet.next()) {
                long dbUserId = resultSet.getLong("user_id");
                String dbUsername = resultSet.getString("username");
                String dbPassword = resultSet.getString("password");
                String dbName = resultSet.getString("name");
                String dbEmail = resultSet.getString("email");
                String dbRole = resultSet.getString("role");
                Date dbCreationDate = resultSet.getDate("creation_date");
                Date dbModificationDate = resultSet.getDate("modification_date");

                return new User(dbUserId, dbUsername, dbPassword, dbName, dbEmail,
                        dbRole, dbCreationDate, dbModificationDate);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unexpected error occurred while trying to get user.", e);
        } finally {
            connection.close();
        }
    }

    /*** ACTIVITIES  ***/

    public void deleteActivities() throws SQLException {
        String query = "DELETE FROM activities";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Unexpected error occurred while trying to delete activities.", e);
        } finally {
            connection.close();
        }
    }

    public Activity getActivityByName(String name) throws SQLException {
        String query = "SELECT activity_id, type, name, description, prize, creation_date, modification_date" +
                " FROM users WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set query params.
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Extract data from result set.
            if (resultSet.next()) {
                long dbActivityId = resultSet.getLong("activity_id");
                String dbType = resultSet.getString("type");
                String dbName = resultSet.getString("name");
                String dbDescription = resultSet.getString("description");
                BigDecimal dbPrize = resultSet.getBigDecimal("prize");
                Date dbCreationDate = resultSet.getDate("creation_date");
                Date dbModificationDate = resultSet.getDate("modification_date");

                return new Activity(dbActivityId, dbType, dbName, dbDescription,
                        dbPrize, dbCreationDate, dbModificationDate);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unexpected error occurred while trying to get activity.", e);
        } finally {
            connection.close();
        }
    }

    /*** BOOKINGS  ***/

    public void deleteBookings() throws SQLException {
        String query = "DELETE FROM bookings";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Unexpected error occurred while trying to delete bookings.", e);
        } finally {
            connection.close();
        }
    }

    public Booking getBookingByStatus(String status) throws SQLException {
        String query = "SELECT booking_id, user_id, activity_id, start_time, end_time, status, creation_date, modification_date" +
                " FROM bookings WHERE status = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set query params.
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Extract data from result set.
            if (resultSet.next()) {
                long dbBookingId = resultSet.getLong("booking_id");
                long dbUserId = resultSet.getLong("user_id");
                long dbActivityId = resultSet.getLong("activity_id");
                Date dbStartTime = resultSet.getDate("start_time");
                Date dbEndTime = resultSet.getDate("end_time");
                String dbStatus = resultSet.getString("status");
                Date dbCreationDate = resultSet.getDate("creation_date");
                Date dbModificationDate = resultSet.getDate("modification_date");

                return new Booking(dbBookingId, dbUserId, dbActivityId, dbStartTime, dbEndTime,
                        dbStatus, dbCreationDate, dbModificationDate);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unexpected error occurred while trying to get booking.", e);
        } finally {
            connection.close();
        }
    }
}
