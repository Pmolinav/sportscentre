package net.pmolinav.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.pmolinav.bookings.dto.ChangeType;
import net.pmolinav.bookings.dto.UserDTO;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.model.User;
import net.pmolinav.springboot.security.WebSecurityConfig;
import net.pmolinav.springboot.service.UserService;
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
public class UserBOController {

    @Autowired
    private UserService userService;

    //TODO: Validar JSON con @Valid y BindingResult. Añadir validaciones en los DTOs.
    //TODO: Fix tests if necessary

    @GetMapping
    @Operation(summary = "Retrieve all users", description = "Bearer token is required to authorize users.")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return ResponseEntity.ok(users);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Bearer token is required to authorize users.")
    public ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO) {
        String message = validateMandatoryFieldsInRequest(userDTO);
        if (!StringUtils.hasText(message)) {
            // Encode password before save user.
            userDTO.setPassword(WebSecurityConfig.passwordEncoder().encode(userDTO.getPassword()));
            Long createdUserId = userService.createUser(userDTO);
            return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a specific user by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //TODO: Complete
//    @PutMapping("{id}")
//    @Operation(summary = "Update a specific user", description = "Bearer token is required to authorize users.")
//    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserDTO userDetails) {
//
//        String message = validateMandatoryFieldsInRequest(userDetails);
//
//        try {
//            User updatedUser = userService.findById(id);
//
//            if (!StringUtils.hasText(message)) {
//                updatedUser.setUsername(userDetails.getUsername());
//                // Encode password before update user.
//                updatedUser.setPassword(WebSecurityConfig.passwordEncoder().encode(userDetails.getPassword()));
//                if (userDetails.getRole() != null) {
//                    updatedUser.setRole(userDetails.getRole().name());
//                }
//                userService.createUser(updatedUser);
//                return ResponseEntity.ok(updatedUser);
//            } else {
//                throw new BadRequestException(message);
//            }
//        } catch (NotFoundException e) {
//            logger.error(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an user by Id", description = "Bearer token is required to authorize users.")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
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
