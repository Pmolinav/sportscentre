package net.pmolinav.springboot.controller;

import lombok.AllArgsConstructor;
import net.pmolinav.bookings.dto.ActivityDTO;
import net.pmolinav.bookings.exception.InternalServerErrorException;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.model.Activity;
import net.pmolinav.springboot.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("activities")
public class ActivityController {

    //TODO: Fix tests if necessary

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        try {
            List<Activity> activities = activityService.findAllActivities();
            return ResponseEntity.ok(activities);
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Long> createActivity(@RequestBody ActivityDTO activityDTO) {
        try {
            Activity createdActivity = activityService.createActivity(activityDTO);
            return new ResponseEntity<>(createdActivity.getActivityId(), HttpStatus.CREATED);
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable long id) {
        try {
            Activity activity = activityService.findById(id);
            return ResponseEntity.ok(activity);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

// TODO: Complete
//    @PutMapping("{id}")
//    @Operation(summary = "Update a specific activity", description = "Bearer token is required to authorize users.")
//    public ResponseEntity<Activity> updateActivity(@RequestParam String requestUid, @PathVariable long id, @RequestBody ActivityDTO activityDetails) {
//        String message = validateMandatoryFieldsInRequest(activityDetails);
//        try {
//            Activity updatedActivity = activityService.findById(id);
//
//            if (!StringUtils.hasText(message)) {
//                updatedActivity.setName(activityDetails.getName());
//                updatedActivity.setDescription(activityDetails.getDescription());
//                updatedActivity.setPrice(activityDetails.getPrice());
//                if (activityDetails.getType() != null) {
//                    updatedActivity.setType(activityDetails.getType().name());
//                }
//                activityService.createActivity(updatedActivity);
//                return ResponseEntity.ok(updatedActivity);
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
    public ResponseEntity<?> deleteActivity(@PathVariable long id) {
        try {
            activityService.deleteActivity(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
