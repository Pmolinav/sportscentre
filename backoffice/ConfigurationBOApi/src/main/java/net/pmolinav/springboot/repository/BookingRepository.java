package net.pmolinav.springboot.repository;

import net.pmolinav.springboot.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // All crud database methods

}


