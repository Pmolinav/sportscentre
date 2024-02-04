package net.pmolinav.configuration.service;

import feign.FeignException;
import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.model.Activity;
import net.pmolinav.configuration.client.ActivityClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ActivityService {
    //TODO: Complete all services and log message
    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityClient activityClient;

    public List<Activity> findAllActivities() {
        try {
            List<Activity> activityList = activityClient.getAllActivities();
            if (CollectionUtils.isEmpty(activityList)) {
                logger.error("No activities found.");
                throw new NotFoundException("No activities found.");
            } else {
                return activityList;
            }
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
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
            return activityClient.getActivityById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Activity with id %s does not exist.", id)));
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public void deleteActivity(Long id) {
        try {
            Activity activity = activityClient.getActivityById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Activity with id %s does not exist.", id)));

            activityClient.deleteActivity(activity.getActivityId());
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }
}
