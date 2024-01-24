package net.pmolinav.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.pmolinav.springboot.dto.ChangeType;
import net.pmolinav.springboot.dto.UserDTO;
import net.pmolinav.springboot.exception.BadRequestException;
import net.pmolinav.springboot.exception.NotFoundException;
import net.pmolinav.springboot.mapper.UserMapper;
import net.pmolinav.springboot.model.User;
import net.pmolinav.springboot.repository.UserRepository;
import net.pmolinav.springboot.security.WebSecurityConfig;
import net.pmolinav.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("users")
@SecurityRequirement(name = "BearerToken")
@Tag(name = "2. User", description = "The User Controller. Contains all the operations that can be performed on an user.")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    //TODO: Validar JSON con @Valid y BindingResult. Añadir validaciones en los DTOs.
    //TODO: Fix tests if necessary

    @GetMapping
    @Operation(summary = "Retrieve all users", description = "Bearer token is required to authorize users.")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Bearer token is required to authorize users.")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        String message = validateMandatoryFieldsInRequest(user);
        if (!StringUtils.hasText(message)) {
            // Encode password before save user.
            user.setPassword(WebSecurityConfig.passwordEncoder().encode(user.getPassword()));
            User createdUser = userService.createUser(userMapper.userDTOToUserEntity(user));
            insertIntoKafka(ChangeType.CREATE_USER, createdUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            logger.error(message);
            throw new BadRequestException(message);
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a specific user by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a specific user", description = "Bearer token is required to authorize users.")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserDTO userDetails) {

        String message = validateMandatoryFieldsInRequest(userDetails);

        try {
            User updatedUser = userService.findById(id);

            if (!StringUtils.hasText(message)) {
                updatedUser.setUsername(userDetails.getUsername());
                // Encode password before update user.
                updatedUser.setPassword(WebSecurityConfig.passwordEncoder().encode(userDetails.getPassword()));
                if (userDetails.getRole() != null) {
                    updatedUser.setRole(userDetails.getRole().name());
                }
                userService.createUser(updatedUser);
                return ResponseEntity.ok(updatedUser);
            } else {
                throw new BadRequestException(message);
            }
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an user by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String validateMandatoryFieldsInRequest(UserDTO userDTO) {
        StringBuilder messageBuilder = new StringBuilder();
        if (userDTO == null) {
            messageBuilder.append("Body is mandatory.");
        } else if (!StringUtils.hasText(userDTO.getUsername())) {
            messageBuilder.append("Username is mandatory.");
        } else if (!StringUtils.hasText(userDTO.getName())) {
            messageBuilder.append("Name is mandatory.");
        } else if (!StringUtils.hasText(userDTO.getPassword())) {
            messageBuilder.append("Password is mandatory.");
        } else if (!StringUtils.hasText(userDTO.getEmail())) {
            messageBuilder.append("Email is mandatory.");
        }
        return messageBuilder.toString();
    }

    private void insertIntoKafka(ChangeType changeType, Object newEntity) {
        //TODO: Complete insert into Kafka
    }
}
