package net.pmolinav.configuration.service;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.exception.CustomStatusException;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.configuration.client.BookingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingBOService {

    @Autowired
    private BookingClient bookingClient;

    public List<Booking> findAllBookings() {
        try {
            return bookingClient.findAllBookings();
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("No bookings found", e);
                throw new NotFoundException("No bookings found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public Long createBooking(BookingDTO bookingDTO) {
        try {
            return bookingClient.createBooking(bookingDTO);
        } catch (FeignException e) {
            log.error("Unexpected error while calling service with status code {}.", e.status(), e);
            throw new CustomStatusException(e.getMessage(), e.status());
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public Booking findBookingById(long id) {
        try {
            return bookingClient.findBookingById(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("Booking with id {} not found.", id, e);
                throw new NotFoundException("Booking " + id + " not found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public void deleteBooking(Long id) {
        try {
            bookingClient.deleteBooking(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("Booking with id {} not found.", id, e);
                throw new NotFoundException("Booking " + id + " not found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
