package net.pmolinav.configuration.client;

import net.pmolinav.bookings.dto.BookingDTO;
import net.pmolinav.bookings.dto.BookingUpdateDTO;
import net.pmolinav.bookings.model.Booking;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "BookingService", url = "localhost:8001/bookings")
public interface BookingClient {

    @GetMapping("/")
    List<Booking> getAllBookings();

    @PostMapping("/")
    Long createBooking(@RequestBody BookingDTO bookingDTO);

    @GetMapping("/{id}")
    Optional<Booking> getBookingById(@PathVariable long id);

    @PutMapping("/{id}")
    Booking updateBooking(@PathVariable long id, @RequestBody BookingUpdateDTO bookingDetails);

    @DeleteMapping("/{id}")
    public HttpStatus deleteBooking(@PathVariable long id);
}
