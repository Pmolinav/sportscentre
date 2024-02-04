package net.pmolinav.bookings.repository;

import net.pmolinav.bookingslib.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // All crud database methods

}


