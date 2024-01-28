package net.pmolinav.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.pmolinav.bookings.dto.ActivityDTO;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.model.Activity;
import net.pmolinav.springboot.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("activities")
@SecurityRequirement(name = "BearerToken")
@Tag(name = "3. Activity", description = "The Activity Controller. Contains all the operations that can be performed on an activity.")
public class ActivityBOController {

    //TODO: Fix tests if necessary

    @Autowired
    private ActivityService activityService;

    @GetMapping
    @Operation(summary = "Retrieve all activities", description = "Bearer token is required to authorize users.")
    public ResponseEntity<List<Activity>> getAllActivities() {
        try {
            List<Activity> activities = activityService.findAllActivities();
            return ResponseEntity.ok(activities);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new activity", description = "Bearer token is required to authorize users.")
    public ResponseEntity<Long> createActivity(@RequestBody ActivityDTO activityDTO) {
        String message = validateMandatoryFieldsInRequest(activityDTO);
        if (!StringUtils.hasText(message)) {
            Long createdActivityId = activityService.createActivity(activityDTO);
            return new ResponseEntity<>(createdActivityId, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a specific activity by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<Activity> getActivityById(@PathVariable long id) {
        try {
            Activity activity = activityService.findActivityById(id);
            return ResponseEntity.ok(activity);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
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
    @Operation(summary = "Delete an activity by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<?> deleteActivity(@PathVariable long id) {
        try {
            activityService.deleteActivity(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String validateMandatoryFieldsInRequest(ActivityDTO activityDTO) {
        StringBuilder messageBuilder = new StringBuilder();
        if (activityDTO == null) {
            messageBuilder.append("Body is mandatory.");
        } else if (activityDTO.getType() == null) {
            messageBuilder.append("Activity type is mandatory.");
        } else if (!StringUtils.hasText(activityDTO.getName())) {
            messageBuilder.append("Activity name is mandatory.");
        } else if (activityDTO.getPrice() == null || activityDTO.getPrice().equals(BigDecimal.ZERO)) {
            messageBuilder.append("Activity price is mandatory and must be greater than zero.");
        }
        return messageBuilder.toString();
    }
}
