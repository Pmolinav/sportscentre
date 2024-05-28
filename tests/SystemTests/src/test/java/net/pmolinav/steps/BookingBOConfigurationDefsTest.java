package net.pmolinav.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.dto.BookingStatus;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.database.SportsCentreDatabaseConnector;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BookingBOConfigurationDefsTest extends BaseSystemTest {

    private final String localURL = "http://localhost:8082";


    @When("^try to create a new booking with data$")
    public void tryToCreateANewBooking(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        try {
            for (Map<String, String> row : rows) {
                executePost(localURL + "/bookings",
                        objectMapper.writeValueAsString(new BookingDTO(lastUser.getUserId(),
                                lastActivity.getActivityId(),
                                new Date(Long.parseLong(row.get("start_time"))),
                                new Date(Long.parseLong(row.get("end_time"))),
                                BookingStatus.valueOf(row.get("status")),
                                row.get("creation_date") != null ? new Date(Long.parseLong(row.get("creation_date")))
                                        : new Date(Instant.now().toEpochMilli() + 10000),
                                row.get("modification_date") != null ? new Date(Long.parseLong(row.get("modification_date")))
                                        : new Date(Instant.now().toEpochMilli() + 10000)
                        )));
            }
        } catch (Exception e) {
            fail();
        }
    }

    @When("^try to get all bookings")
    public void tryToGetAllBookings() {
        executeGet(localURL + "/bookings");
    }

    @When("^try to get a booking by bookingId$")
    public void tryToGetABookingByBookingId() {
        executeGet(localURL + "/bookings/" + lastBooking.getBookingId());
    }

    @When("^try to delete a booking by bookingId$")
    public void tryToDeleteAnUserByUserId() {
        executeDelete(localURL + "/bookings/" + lastBooking.getBookingId());
    }

    @Then("a booking with status (.*) has been stored successfully$")
    public void aBookingHasBeenStored(String status) {
        try {
            dbConnector = new SportsCentreDatabaseConnector();
            lastBooking = dbConnector.getBookingByStatus(status);
            assertNotNull(lastBooking);
        } catch (SQLException e) {
            fail();
        }
    }

    @Then("a list of bookings with statuses (.*) are returned in response$")
    public void aListOfBookingsWithStatusesIsReturned(String statuses) throws JsonProcessingException {
        List<String> statusesList = List.of(statuses.split(","));
        List<Booking> obtainedBookings = objectMapper.readValue(latestResponse.getBody(), new TypeReference<List<Booking>>() {
        });
        assertNotNull(obtainedBookings);
        for (String status : statusesList) {
            assertTrue(obtainedBookings.stream().anyMatch(booking -> booking.getStatus().equals(status)));
        }
    }

    @Then("a booking with status (.*) is returned in response$")
    public void aBookingWithStatusIsReturned(String status) throws JsonProcessingException {
        Booking obtainedBooking = objectMapper.readValue(latestResponse.getBody(), Booking.class);
        assertNotNull(obtainedBooking);
        assertEquals(status, obtainedBooking.getStatus());
    }

}