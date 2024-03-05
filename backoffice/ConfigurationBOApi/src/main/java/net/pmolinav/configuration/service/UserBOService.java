package net.pmolinav.configuration.service;

import feign.FeignException;
import feign.RetryableException;
import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.exception.UnexpectedException;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserBOService {
    //TODO: Complete all services and log message
    private static final Logger logger = LoggerFactory.getLogger(ActivityBOService.class);

    @Autowired
    private UserClient userClient;

    public List<User> findAllUsers() {
        try {
            return userClient.findAllUsers();
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            } else {
                logger.warn("No users found", e);
                throw new NotFoundException("No users found");
            }
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
            return userClient.findUserById(id);
        } catch (FeignException e) {
            if (e.status() == NOT_FOUND.value()) {
                logger.warn("User with id " + id + " not found", e);
                throw new NotFoundException("User " + id + " not found");
            } else {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            }
        }
    }

    public User findUserByUsername(String username) {
        try {
            return userClient.findUserByUsername(username);
        } catch (FeignException e) {
            if (e.status() == NOT_FOUND.value()) {
                logger.warn("User with username " + username + " not found", e);
                throw new NotFoundException("User " + username + " not found");
            } else {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            }
        }
    }

    public void deleteUser(Long id) {
        try {
            userClient.deleteUser(id);
        } catch (FeignException e) {
            if (e.status() == NOT_FOUND.value()) {
                logger.warn("User with id " + id + " not found", e);
                throw new NotFoundException("User " + id + " not found");
            } else {
                logger.error("Unexpected error while calling service with status code " + e.status(), e);
                throw new UnexpectedException(e.getMessage(), e.status());
            }
        }
    }
}
