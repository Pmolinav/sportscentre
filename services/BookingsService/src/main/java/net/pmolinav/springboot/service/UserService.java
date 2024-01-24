package net.pmolinav.springboot.service;

import net.pmolinav.springboot.exception.NotFoundException;
import net.pmolinav.bookings.model.User;
import net.pmolinav.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService {
    //TODO: Complete all services

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        if (CollectionUtils.isEmpty(userList)) {
            throw new NotFoundException("No users found.");
        } else {
            return userList;
        }
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));

        userRepository.delete(user);
    }
}
