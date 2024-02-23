package net.pmolinav.configuration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.pmolinav.bookingslib.dto.BookingDTO;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.mapper.BookingMapper;
import net.pmolinav.bookingslib.model.Booking;
import net.pmolinav.configuration.service.BookingBOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("bookings")
@SecurityRequirement(name = "BearerToken")
@Tag(name = "4. Booking", description = "The Booking Controller. Contains all the operations that can be performed on a booking.")
public class BookingBOController {

    //TODO: Fix tests if necessary
    @Autowired
    private BookingBOService bookingBOService;
    @Autowired
    private BookingMapper bookingMapper;

    @GetMapping
    @Operation(summary = "Retrieve all bookings", description = "Bearer token is required to authorize users.")
    public ResponseEntity<List<Booking>> findAllBookings() {
        try {
            List<Booking> bookings = bookingBOService.findAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.notFound().build();
        } catch (UnexpectedException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Bearer token is required to authorize users.")
    public ResponseEntity<Long> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        String message = validateMandatoryFieldsInRequest(bookingDTO);
        try {
            if (!StringUtils.hasText(message)) {
                Long createdActivityId = bookingBOService.createBooking(bookingDTO);
                return new ResponseEntity<>(createdActivityId, HttpStatus.CREATED);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (UnexpectedException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a specific booking by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<Booking> getBookingById(@PathVariable long id) {
        try {
            Booking booking = bookingBOService.findBookingById(id);
            return ResponseEntity.ok(booking);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnexpectedException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
// TODO: Complete
//    @PutMapping("{id}")
//    @Operation(summary = "Update a specific booking", description = "Bearer token is required to authorize users.")
//    public ResponseEntity<Booking> updateBooking(@PathVariable long id, @RequestBody BookingUpdateDTO bookingDetails) {
//
//        String message = validateMandatoryFieldsInUpdateRequest(bookingDetails);
//
//        try {
//            Booking updatedBooking = bookingService.findById(id);
//
//            if (!StringUtils.hasText(message)) {
//                updatedBooking.setStartTime(bookingDetails.getStartTime());
//                updatedBooking.setEndTime(bookingDetails.getEndTime());
//                bookingService.createBooking(updatedBooking);
//                return ResponseEntity.ok(updatedBooking);
//            } else {
//                logger.error(message);
//                throw new BadRequestException(message);
//            }
//        } catch (NotFoundException e) {
//            logger.error(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a booking by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable long id) {
        try {
            bookingBOService.deleteBooking(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnexpectedException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String validateMandatoryFieldsInRequest(BookingDTO bookingDTO) {
        StringBuilder messageBuilder = new StringBuilder();
        if (bookingDTO.getUserId() == 0 || bookingDTO.getActivityId() == 0) {
            messageBuilder.append("User and activity ids are mandatory to associate the booking correctly.");
        } else if (bookingDTO.getStartTime().compareTo(bookingDTO.getEndTime()) >= 0) {
            messageBuilder.append("End time must be greater than start time.");
        }
        return messageBuilder.toString();
    }

//    private String validateMandatoryFieldsInUpdateRequest(BookingUpdateDTO bookingDTO) {
//        StringBuilder messageBuilder = new StringBuilder();
//        if (bookingDTO == null) {
//            messageBuilder.append("Body is mandatory.");
//        } else if (bookingDTO.getStartTime() == null || bookingDTO.getEndTime() == null) {
//            messageBuilder.append("Start time and end time are mandatory.");
//        } else if (bookingDTO.getStartTime().compareTo(bookingDTO.getEndTime()) >= 0) {
//            messageBuilder.append("End time must be greater than start time.");
//        }
//        return messageBuilder.toString();
//    }
}
