package net.pmolinav.configuration.service;

import feign.FeignException;
import feign.RetryableException;
import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.configuration.client.ActivityClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityBOService {
    //TODO: Complete all services and log message
    private static final Logger logger = LoggerFactory.getLogger(ActivityBOService.class);

    @Autowired
    private ActivityClient activityClient;

    public List<Activity> findAllActivities() {
        try {
            return activityClient.findAllActivities();
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            } else {
                logger.warn("No activities found", e);
                throw new NotFoundException("No activities found");
            }
        }
    }

    public Long createActivity(ActivityDTO activityDTO) {
        try {
            return activityClient.createActivity(activityDTO);
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public Activity findActivityById(long id) {
        try {
            return activityClient.findActivityById(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            } else {
                logger.warn("Activity with id " + id + " not found", e);
                throw new NotFoundException("Activity " + id + " not found");
            }
        }
    }

    public void deleteActivity(Long id) {
        try {
            activityClient.deleteActivity(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            } else {
                logger.warn("Activity with id " + id + " not found", e);
                throw new NotFoundException("Activity " + id + " not found");
            }
        }
    }
}
