package net.pmolinav.springboot.controller;

import net.pmolinav.springboot.security.AuthCredentials;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping()
    public void login(@RequestBody AuthCredentials authCredentials) {
        //TODO: It is working correctly by delegating calls to security login.
    }
}
