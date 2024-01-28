package net.pmolinav.springboot.client;

import net.pmolinav.bookings.dto.UserDTO;
import net.pmolinav.bookings.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "UserService", url = "localhost:8001/users")
public interface UserClient {

    @GetMapping("/")
    List<User> getAllUsers();

    @PostMapping("/")
    Long createUser(@RequestBody UserDTO userDTO);

    @GetMapping("/{id}")
    Optional<User> getUserById(@PathVariable long id);

//    @PutMapping("/{id}")
//    User updateUser(@PathVariable long id, @RequestBody UserUpdateDTO userDetails);

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id);
}
