package net.pmolinav.configuration.service;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.exception.CustomStatusException;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.configuration.client.ActivityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityBOService {

    @Autowired
    private ActivityClient activityClient;

    public List<Activity> findAllActivities() {
        try {
            return activityClient.findAllActivities();
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("No activities found.", e);
                throw new NotFoundException("No activities found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public String createActivity(ActivityDTO activityDTO) {
        try {
            return activityClient.createActivity(activityDTO);
        } catch (FeignException e) {
            log.error("Unexpected error while calling service with status code {}.", e.status(), e);
            throw new CustomStatusException(e.getMessage(), e.status());
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public Activity findActivityByName(String name) {
        try {
            return activityClient.findActivityByName(name);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("Activity with name {} not found.", name, e);
                throw new NotFoundException("Activity " + name + " not found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public void deleteActivity(String name) {
        try {
            activityClient.deleteActivity(name);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected retryable error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("Activity with id {} not found.", name, e);
                throw new NotFoundException("Activity " + name + " not found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
