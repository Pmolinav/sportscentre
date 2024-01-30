package net.pmolinav.bookings.service;

import net.pmolinav.bookings.dto.BookingDTO;
import net.pmolinav.bookings.exception.InternalServerErrorException;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.mapper.BookingMapper;
import net.pmolinav.bookings.model.Booking;
import net.pmolinav.bookings.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    //TODO: Complete all services
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingMapper bookingMapper;

    @Transactional(readOnly = true)
    public List<Booking> findAllBookings() {
        try {
            return bookingRepository.findAll();
        } catch (Exception e) {
            logger.error("Unexpected error while searching all bookings in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional
    public Booking createBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = bookingMapper.bookingDTOToBookingEntity(bookingDTO);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            logger.error("Unexpected error while creating new booking in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Booking findById(long id) {
        try {
            return bookingRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));
        } catch (Exception e) {
            logger.error("Unexpected error while searching booking with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional
    public void deleteBooking(Long id) {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("Booking with id %s does not exist.", id)));

            bookingRepository.delete(booking);
        } catch (Exception e) {
            logger.error("Unexpected error while removing booking with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
