package net.pmolinav.bookings.controller;

import lombok.AllArgsConstructor;
import net.pmolinav.bookings.dto.BookingDTO;
import net.pmolinav.bookings.exception.InternalServerErrorException;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.model.Booking;
import net.pmolinav.bookings.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("bookings")
public class BookingController {

    //TODO: Fix tests if necessary

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<Booking>> findAllBookings() {
        try {
            List<Booking> bookings = bookingService.findAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Long> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            Booking createdBooking = bookingService.createBooking(bookingDTO);
            return new ResponseEntity<>(createdBooking.getBookingId(), HttpStatus.CREATED);
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<Booking> findBookingById(@PathVariable long id) {
        try {
            Booking booking = bookingService.findById(id);
            return ResponseEntity.ok(booking);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

// TODO: Complete
//    @PutMapping("{id}")
//    @Operation(summary = "Update a specific booking", description = "Bearer token is required to authorize users.")
//    public ResponseEntity<Booking> updateBooking(@RequestParam String requestUid, @PathVariable long id, @RequestBody BookingDTO bookingDetails) {
//        String message = validateMandatoryFieldsInRequest(bookingDetails);
//        try {
//            Booking updatedBooking = bookingService.findById(id);
//
//            if (!StringUtils.hasText(message)) {
//                updatedBooking.setName(bookingDetails.getName());
//                updatedBooking.setDescription(bookingDetails.getDescription());
//                updatedBooking.setPrice(bookingDetails.getPrice());
//                if (bookingDetails.getType() != null) {
//                    updatedBooking.setType(bookingDetails.getType().name());
//                }
//                bookingService.createBooking(updatedBooking);
//                return ResponseEntity.ok(updatedBooking);
//            } else {
//                return ResponseEntity.badRequest().build();
//            }
//        } catch (NotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (UnexpectedException e) {
//            return ResponseEntity.status(e.getStatusCode()).build();
//        }
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
