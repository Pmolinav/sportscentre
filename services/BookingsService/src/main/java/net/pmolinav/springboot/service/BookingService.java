package net.pmolinav.springboot.service;

import net.pmolinav.springboot.exception.NotFoundException;
import net.pmolinav.springboot.model.Booking;
import net.pmolinav.springboot.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookingService {
    //TODO: Complete all services

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public List<Booking> findAllBookings() {
        List<Booking> bookingList = bookingRepository.findAll();
        if (CollectionUtils.isEmpty(bookingList)) {
            throw new NotFoundException("No bookings found.");
        } else {
            return bookingList;
        }
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking findById(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));
    }

    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));

        bookingRepository.delete(booking);
    }
}
