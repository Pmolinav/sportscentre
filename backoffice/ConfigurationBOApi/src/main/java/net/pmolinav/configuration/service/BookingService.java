package net.pmolinav.configuration.service;

import feign.FeignException;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.configuration.client.BookingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookingService {
    //TODO: Complete all services and log message
    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private BookingClient bookingClient;

    public List<Booking> findAllBookings() {
        try {
            List<Booking> bookingList = bookingClient.getAllBookings();
            if (CollectionUtils.isEmpty(bookingList)) {
                logger.error("No bookings found.");
                throw new NotFoundException("No bookings found.");
            } else {
                return bookingList;
            }
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public Long createBooking(BookingDTO bookingDTO) {
        try {
            return bookingClient.createBooking(bookingDTO);
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public Booking findBookingById(long id) {
        try {
            return bookingClient.getBookingById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public void deleteBooking(Long id) {
        try {
            Booking booking = bookingClient.getBookingById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));

            bookingClient.deleteBooking(booking.getBookingId());
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

}
