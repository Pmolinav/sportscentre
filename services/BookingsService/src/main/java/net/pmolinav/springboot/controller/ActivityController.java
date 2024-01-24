package net.pmolinav.springboot.controller;

import lombok.AllArgsConstructor;
import net.pmolinav.springboot.exception.BadRequestException;
import net.pmolinav.springboot.exception.NotFoundException;
import net.pmolinav.springboot.mapper.ActivityMapper;
import net.pmolinav.bookings.dto.ActivityDTO;
import net.pmolinav.bookings.model.Activity;
import net.pmolinav.springboot.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ActivityController {

    //TODO: Add logs
    //TODO: Fix tests if necessary
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityMapper activityMapper;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        try {
            List<Activity> activities = activityService.findAllActivities();
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO activityDTO) {
        String message = validateMandatoryFieldsInRequest(activityDTO);
        if (!StringUtils.hasText(message)) {
            Activity createdActivity = activityService.createActivity(activityMapper.activityDTOToActivityEntity(activityDTO));
            return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
        } else {
            logger.error(message);
            throw new BadRequestException(message);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable long id) {
        try {
            Activity activity = activityService.findById(id);
            return ResponseEntity.ok(activity);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable long id, @RequestBody ActivityDTO activityDetails) {

        String message = validateMandatoryFieldsInRequest(activityDetails);

        try {
            Activity updatedActivity = activityService.findById(id);

            if (!StringUtils.hasText(message)) {
                updatedActivity.setName(activityDetails.getName());
                updatedActivity.setDescription(activityDetails.getDescription());
                updatedActivity.setPrice(activityDetails.getPrice());
                if (activityDetails.getType() != null) {
                    updatedActivity.setType(activityDetails.getType().name());
                }
                activityService.createActivity(updatedActivity);
                return ResponseEntity.ok(updatedActivity);
            } else {
                logger.error(message);
                throw new BadRequestException(message);
            }
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable long id) {
        try {
            activityService.deleteActivity(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
