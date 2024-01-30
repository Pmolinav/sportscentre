package net.pmolinav.bookings.repository;

import net.pmolinav.bookings.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // All crud database methods

}


