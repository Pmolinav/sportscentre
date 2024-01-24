package net.pmolinav.springboot.service;

import net.pmolinav.springboot.dto.BookingDTO;
import net.pmolinav.springboot.exception.NotFoundException;
import net.pmolinav.springboot.client.BookingsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookingService {
    //TODO: Complete all services

    @Autowired
    private BookingsClient bookingsClient;

    public List<Booking> findAllBookings() {
        ResponseEntity<List<BookingDTO>> bookingList = bookingsClient.getAllBookings();
        if (CollectionUtils.isEmpty(bookingList)) {
            throw new NotFoundException("No bookings found.");
        } else {
            return bookingList;
        }
    }

    public BookingDTO createBooking(BookingDTO booking) {
        return bookingsClient.save(booking);
    }

    public BookingDTO findById(long id) {
        return bookingsClient.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));
    }

    public void deleteBooking(Long id) {
        BookingDTO booking = bookingsClient.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));

        bookingsClient.delete(booking);
    }
}
