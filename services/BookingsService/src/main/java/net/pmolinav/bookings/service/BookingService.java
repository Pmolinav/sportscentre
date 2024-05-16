package net.pmolinav.bookings.service;

import net.pmolinav.bookings.repository.BookingRepository;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.mapper.BookingMapper;
import net.pmolinav.bookingslib.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookingService {
    //TODO: Complete all services
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingService(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }

    @Transactional(readOnly = true)
    public List<Booking> findAllBookings() {
        List<Booking> bookingsList;
        try {
            bookingsList = bookingRepository.findAll();
        } catch (Exception e) {
            logger.error("Unexpected error while searching all bookings in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
        if (CollectionUtils.isEmpty(bookingsList)) {
            throw new NotFoundException("No bookings found in repository.");
        } else {
            return bookingsList;
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
        } catch (NotFoundException e) {
            logger.error("Booking with id " + id + " was not found.", e);
            throw e;
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
        } catch (NotFoundException e) {
            logger.error("Booking with id " + id + " was not found.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while removing booking with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
