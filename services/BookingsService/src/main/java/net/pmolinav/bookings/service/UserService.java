package net.pmolinav.bookings.service;

import net.pmolinav.bookings.repository.UserRepository;
import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.exception.InternalServerErrorException;
import net.pmolinav.bookingslib.exception.NotFoundException;
import net.pmolinav.bookingslib.mapper.UserMapper;
import net.pmolinav.bookingslib.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        List<User> usersList;
        try {
            usersList = userRepository.findAll();
        } catch (Exception e) {
            logger.error("Unexpected error while searching all users in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
        if (CollectionUtils.isEmpty(usersList)) {
            logger.warn("No users were found in repository.");
            throw new NotFoundException("No users found in repository.");
        } else {
            return usersList;
        }
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        try {
            User user = userMapper.userDTOToUserEntity(userDTO);
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Unexpected error while creating new user in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
        } catch (NotFoundException e) {
            logger.error("User with id " + id + " was not found.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while searching user with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException(String.format("User with username %s does not exist.", username)));
        } catch (NotFoundException e) {
            logger.error("User with username " + username + " was not found.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while searching user with username " + username + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));

            userRepository.delete(user);
        } catch (NotFoundException e) {
            logger.error("User with id " + id + " was not found.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while removing user with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
