package net.pmolinav.bookings.service;

import net.pmolinav.bookings.repository.ActivityRepository;
import net.pmolinav.bookingslib.dto.ActivityDTO;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.mapper.ActivityMapper;
import net.pmolinav.bookingslib.model.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ActivityService {
    //TODO: Complete all services
    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Autowired
    public ActivityService(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    @Transactional(readOnly = true)
    public List<Activity> findAllActivities() {
        List<Activity> activityList;
        try {
            activityList = activityRepository.findAll();
        } catch (Exception e) {
            logger.error("Unexpected error while searching all activities in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
        if (CollectionUtils.isEmpty(activityList)) {
            throw new NotFoundException("No activities found in repository.");
        } else {
            return activityList;
        }
    }

    @Transactional
    public Activity createActivity(ActivityDTO activityDTO) {
        try {
            Activity activity = activityMapper.activityDTOToActivityEntity(activityDTO);
            return activityRepository.save(activity);
        } catch (Exception e) {
            logger.error("Unexpected error while creating new activity in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Activity findById(long id) {
        try {
            return activityRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("Activity with id %s does not exist.", id)));
        } catch (NotFoundException e) {
            logger.error("Activity with id " + id + " was not found.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while searching activity with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional
    public void deleteActivity(Long id) {
        try {
            Activity activity = activityRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("Activity with id %s does not exist.", id)));

            activityRepository.delete(activity);
        } catch (NotFoundException e) {
            logger.error("Activity with id " + id + " was not found.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while removing activity with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
