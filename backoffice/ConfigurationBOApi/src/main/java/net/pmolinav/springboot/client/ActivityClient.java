package net.pmolinav.springboot.client;

import net.pmolinav.bookings.dto.ActivityDTO;
import net.pmolinav.bookings.model.Activity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "ActivityService", url = "localhost:8001/activities")
public interface ActivityClient {

    @GetMapping("/")
    List<Activity> getAllActivities();

    @PostMapping("/")
    Long createActivity(@RequestBody ActivityDTO activityDTO);

    @GetMapping("/{id}")
    Optional<Activity> getActivityById(@PathVariable long id);

//    @PutMapping("/{id}")
//    Activity updateActivity(@PathVariable long id, @RequestBody ActivityUpdateDTO activityDetails);

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable long id);
}
