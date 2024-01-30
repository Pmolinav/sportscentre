package net.pmolinav.bookings.controller;

import lombok.AllArgsConstructor;
import net.pmolinav.bookings.dto.UserDTO;
import net.pmolinav.bookings.exception.InternalServerErrorException;
import net.pmolinav.bookings.exception.NotFoundException;
import net.pmolinav.bookings.model.User;
import net.pmolinav.bookings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("users")
public class UserController {

    //TODO: Fix tests if necessary

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return ResponseEntity.ok(users);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO) {
        try {
            User createdUser = userService.createUser(userDTO);
            return new ResponseEntity<>(createdUser.getUserId(), HttpStatus.CREATED);
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<User> findUserById(@PathVariable long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        try {
            User user = userService.findByUsername(username);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

// TODO: Complete
//    @PutMapping("{id}")
//    @Operation(summary = "Update a specific user", description = "Bearer token is required to authorize users.")
//    public ResponseEntity<User> updateUser(@RequestParam String requestUid, @PathVariable long id, @RequestBody UserDTO userDetails) {
//        String message = validateMandatoryFieldsInRequest(userDetails);
//        try {
//            User updatedUser = userService.findById(id);
//
//            if (!StringUtils.hasText(message)) {
//                updatedUser.setName(userDetails.getName());
//                updatedUser.setDescription(userDetails.getDescription());
//                updatedUser.setPrice(userDetails.getPrice());
//                if (userDetails.getType() != null) {
//                    updatedUser.setType(userDetails.getType().name());
//                }
//                userService.createUser(updatedUser);
//                return ResponseEntity.ok(updatedUser);
//            } else {
//                return ResponseEntity.badRequest().build();
//            }
//        } catch (NotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (UnexpectedException e) {
//            return ResponseEntity.status(e.getStatusCode()).build();
//        }
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InternalServerErrorException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
