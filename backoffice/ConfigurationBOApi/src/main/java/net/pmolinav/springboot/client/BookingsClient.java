package net.pmolinav.springboot.client;

import net.pmolinav.springboot.dto.BookingDTO;
import net.pmolinav.springboot.dto.BookingUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="BookingsService", url="localhost:8001")
public interface BookingsClient {

    @GetMapping("/")
    ResponseEntity<List<BookingDTO>> getAllBookings();

    @PostMapping("/")
    ResponseEntity<Long> createBooking(@RequestBody BookingDTO bookingDTO);

    @GetMapping("/{id}")
    BookingDTO getBookingById(@PathVariable long id);

    @PutMapping("/{id}")
    ResponseEntity<BookingDTO> updateBooking(@PathVariable long id, @RequestBody BookingUpdateDTO bookingDetails);

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable long id);
}
