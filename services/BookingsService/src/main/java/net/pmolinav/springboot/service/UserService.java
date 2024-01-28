package net.pmolinav.springboot.service;

import net.pmolinav.bookings.dto.UserDTO;
import net.pmolinav.bookings.exception.InternalServerErrorException;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.mapper.UserMapper;
import net.pmolinav.bookings.model.User;
import net.pmolinav.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    //TODO: Complete all services
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Unexpected error while searching all users in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
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
        } catch (Exception e) {
            logger.error("Unexpected error while removing user with id " + id + " in repository.", e);
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
