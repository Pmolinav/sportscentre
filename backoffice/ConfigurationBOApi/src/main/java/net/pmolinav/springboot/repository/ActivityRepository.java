package net.pmolinav.springboot.repository;

import net.pmolinav.springboot.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // All crud database methods

}


