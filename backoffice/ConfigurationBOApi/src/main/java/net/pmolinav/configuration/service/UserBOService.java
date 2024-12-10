package net.pmolinav.configuration.service;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.exception.CustomStatusException;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.model.User;
import net.pmolinav.configuration.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserBOService {

    @Autowired
    private UserClient userClient;

    public List<User> findAllUsers() {
        try {
            return userClient.findAllUsers();
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("No users found.", e);
                throw new NotFoundException("No users found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public Long createUser(UserDTO userDTO) {
        try {
            return userClient.createUser(userDTO);
        } catch (FeignException e) {
            log.error("Unexpected error while calling service with status code {}.", e.status(), e);
            throw new CustomStatusException(e.getMessage(), e.status());
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public User findUserById(long id) {
        try {
            return userClient.findUserById(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("User with id {} not found.", id, e);
                throw new NotFoundException("User " + id + " not found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public User findUserByUsername(String username) {
        try {
            return userClient.findUserByUsername(username);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("User with username {} not found.", username, e);
                throw new NotFoundException("User " + username + " not found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            userClient.deleteUser(id);
        } catch (FeignException e) {
            if (e instanceof RetryableException) {
                log.error("Unexpected error while calling service with status code {}.", e.status(), e);
                throw new CustomStatusException(e.getMessage(), e.status());
            } else {
                log.warn("User with id {} not found.", id, e);
                throw new NotFoundException("User " + id + " not found");
            }
        } catch (Exception e) {
            log.error("Unexpected exception occurred while calling service.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
