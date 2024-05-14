package net.pmolinav.configuration.functionals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.dto.BookingStatus;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.configuration.client.BookingClient;
import net.pmolinav.configuration.mapper.BookingMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EntityScan("net.pmolinav.bookingslib.model")
class BookingBOControllerFunctionalTest extends AbstractBaseTest {

    //TODO: Review how to mock Authorization
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private BookingClient bookingClient;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAllBookingsNotFound() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllBookingsHappyPath() throws Exception {
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
        mockMvc.perform(delete("/bookings/5"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/bookings/6"))
                .andExpect(status().isOk());
    }

}

