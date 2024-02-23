package net.pmolinav.configuration.client;

import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.model.Activity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ActivityService", url = "localhost:8001/activities")
public interface ActivityClient {

    @GetMapping("/")
    List<Activity> findAllActivities();

    @PostMapping("/")
    Long createActivity(@RequestBody ActivityDTO activityDTO);

    @GetMapping("/{id}")
    Activity findActivityById(@PathVariable long id);

//    @PutMapping("/{id}")
//    Activity updateActivity(@PathVariable long id, @RequestBody ActivityUpdateDTO activityDetails);

    @DeleteMapping("/{id}")
    void deleteActivity(@PathVariable long id);
}
