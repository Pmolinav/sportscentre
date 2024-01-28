package net.pmolinav.springboot.service;

import feign.FeignException;
import net.pmolinav.bookings.dto.UserDTO;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.exception.UnexpectedException;
import net.pmolinav.bookings.model.User;
import net.pmolinav.springboot.client.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService {
    //TODO: Complete all services and log message
    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private UserClient userClient;

    public List<User> findAllUsers() {
        try {
            List<User> userList = userClient.getAllUsers();
            if (CollectionUtils.isEmpty(userList)) {
                logger.error("No users found.");
                throw new NotFoundException("No users found.");
            } else {
                return userList;
            }
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public Long createUser(UserDTO userDTO) {
        try {
            return userClient.createUser(userDTO);
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public User findUserById(long id) {
        try {
            return userClient.getUserById(id).orElseThrow(
                    () -> new NotFoundException(String.format("User with id %s does not exist.", id)));
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }

    public void deleteUser(Long id) {
        try {
            User user = userClient.getUserById(id).orElseThrow(
                    () -> new NotFoundException(String.format("User with id %s does not exist.", id)));

            userClient.deleteUser(user.getUserId());
        } catch (FeignException e) {
            logger.error("Unexpected error while calling service with status code " + e.status(), e);
            throw new UnexpectedException(e.getMessage(), e.status());
        }
    }
}
