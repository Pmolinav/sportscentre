package net.pmolinav.springboot.service;

import net.pmolinav.springboot.exception.NotFoundException;
import net.pmolinav.bookings.model.Activity;
import net.pmolinav.springboot.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ActivityService {
    //TODO: Complete all services

    @Autowired
    private ActivityRepository activityRepository;

    @Transactional(readOnly = true)
    public List<Activity> findAllActivities() {
        List<Activity> ActivityList = activityRepository.findAll();
        if (CollectionUtils.isEmpty(ActivityList)) {
            throw new NotFoundException("No Activities found.");
        } else {
            return ActivityList;
        }
    }

    @Transactional
    public Activity createActivity(Activity Activity) {
        return activityRepository.save(Activity);
    }

    @Transactional(readOnly = true)
    public Activity findById(long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Activity with id %s does not exist.", id)));
    }

    @Transactional
    public void deleteActivity(Long id) {
        Activity Activity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Activity with id %s does not exist.", id)));

        activityRepository.delete(Activity);
    }
}
