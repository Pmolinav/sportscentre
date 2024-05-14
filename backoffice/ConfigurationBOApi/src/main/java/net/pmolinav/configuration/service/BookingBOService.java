package net.pmolinav.configuration.service;

import feign.FeignException;
import feign.RetryableException;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.configuration.client.BookingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingBOService {
    //TODO: Complete all services and log message
    private static final Logger logger = LoggerFactory.getLogger(ActivityBOService.class);

    @Autowired
    private BookingClient bookingClient;

    public List<Booking> findAllBookings() {
        try {
            return bookingClient.getAllBookings();
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            } else {
                logger.warn("No bookings found", e);
                throw new NotFoundException("No bookings found");
            }
        } catch (Exception e) {
            logger.error("Unexpected exception occurred while calling service.", e);
            throw new UnexpectedException(e.getMessage(), 500);
        }
    }

    public Long createBooking(BookingDTO bookingDTO) {
        try {
            return bookingClient.createBooking(bookingDTO);
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        } catch (Exception e) {
            logger.error("Unexpected exception occurred while calling service.", e);
            throw new UnexpectedException(e.getMessage(), 500);
        }
    }

    public Booking findBookingById(long id) {
        try {
            return bookingClient.findBookingById(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            } else {
                logger.warn("Booking with id " + id + " not found", e);
                throw new NotFoundException("Booking " + id + " not found");
            }
        } catch (Exception e) {
            logger.error("Unexpected exception occurred while calling service.", e);
            throw new UnexpectedException(e.getMessage(), 500);
        }
    }

    public void deleteBooking(Long id) {
        try {
            bookingClient.deleteBooking(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            } else {
                logger.warn("Booking with id " + id + " not found", e);
                throw new NotFoundException("Booking " + id + " not found");
            }
        } catch (Exception e) {
            logger.error("Unexpected exception occurred while calling service.", e);
            throw new UnexpectedException(e.getMessage(), 500);
        }
    }

}
