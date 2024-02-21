package net.pmolinav.bookings.functional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookings.mapper.BookingMapper;
import net.pmolinav.bookings.repository.BookingRepository;
import net.pmolinav.bookings.security.WebSecurityConfig;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.dto.BookingStatus;
import net.pmolinav.bookingslib.model.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@EnableJpaRepositories("net.pmolinav.bookings.repository")
@EntityScan("net.pmolinav.bookingslib.model")
class BookingsControllerFunctionalTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAllBookingsNotFound() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllBookingsHappyPath() throws Exception {
        givenSomeBookingsPreviouslyStoredWithIds(1, 2, true);
        MvcResult result = mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andReturn();

        List<Booking> bookingResponseList = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Booking>>() {
                });

        Assertions.assertEquals(2, bookingResponseList.size());
    }

    @Test
    void createBookingHappyPath() throws Exception {
        givenSomeBookingsPreviouslyStoredWithIds(1, 2, false);
        BookingDTO requestDto = new BookingDTO(1L, 1L, new Date(100),
                new Date(3000), BookingStatus.OPEN, new Date(), null);

        MvcResult result = mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertThat(responseBody, matchesPattern("\\d+"));
    }

    @Test
    void findBookingByIdNotFound() throws Exception {
        mockMvc.perform(get("/bookings/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findBookingByIdHappyPath() throws Exception {
        givenSomeBookingsPreviouslyStoredWithIds(3, 4, true);
        MvcResult result = mockMvc.perform(get("/bookings/3"))
                .andExpect(status().isOk())
                .andReturn();

        Booking bookingResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<Booking>() {
                });

        Assertions.assertEquals(3L, bookingResponse.getActivityId());
        Assertions.assertEquals(3L, bookingResponse.getUserId());
    }

    @Test
    void deleteBookingByIdNotFound() throws Exception {
        mockMvc.perform(delete("/bookings/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBookingByIdHappyPath() throws Exception {
        givenSomeBookingsPreviouslyStoredWithIds(5, 6, true);

        mockMvc.perform(delete("/bookings/5"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/bookings/6"))
                .andExpect(status().isOk());
    }

    private void givenSomeBookingsPreviouslyStoredWithIds(long id, long id2, boolean createBookings) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            try (Statement statement = connection.createStatement()) {

                String insertActivityQuery = "INSERT INTO activities (activity_id, type, name, description, price, creation_date, modification_date) " +
                        "VALUES (" + id + ", 'FOOTBALL', 'Football', 'Football activity', 25, '2024-01-01 08:00:00', '2024-01-01 08:00:00');";
                statement.executeUpdate(insertActivityQuery);

                String insertActivityQuery2 = "INSERT INTO activities (activity_id, type, name, description, price, creation_date, modification_date) " +
                        "VALUES (" + id2 + ", 'TENNIS', 'Tennis', 'Tennis Activity', 15.50, '2023-01-02 10:00:00', '2024-01-02 10:00:00');";
                statement.executeUpdate(insertActivityQuery2);

                String insertUserQuery = "INSERT INTO users (user_id, username, password, name, email, role, creation_date, modification_date) " +
                        "VALUES (" + id + ", 'someUser', '" + WebSecurityConfig.passwordEncoder().encode("somePassword") + "', 'John Doe', 'john@example.com', 'ADMIN', '2024-02-14 10:00:00', NULL);";
                statement.executeUpdate(insertUserQuery);

                String insertUserQuery2 = "INSERT INTO users (user_id, username, password, name, email, role, creation_date, modification_date) " +
                        "VALUES (" + id2 + ", 'otherUser', '" + WebSecurityConfig.passwordEncoder().encode("somePassword") + "', 'Jane Smith', 'jane@example.com', 'ADMIN', '2024-02-14 10:30:00', '2024-02-14 11:15:00');";
                statement.executeUpdate(insertUserQuery2);

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

