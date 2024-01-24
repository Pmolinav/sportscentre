package net.pmolinav.springboot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.pmolinav.springboot.security.AuthCredentials;
import org.springframework.web.bind.annotation.*;

//TODO: ORDER SWAGGER WITHOUT USING NUMBERS IN TAGS TO PUT LOGIN AT FIRST.
@CrossOrigin("*")
@RestController
@RequestMapping("login")
@Tag(name = "1. Login", description = "The Login Controller. Required to authorize users. A valid token is granted and allows valid users to call other controllers with permissions.")
public class LoginController {

    @PostMapping()
    @Operation(summary = "Authorize user", description = "This is a public endpoint. Authentication is not required to call, but requested user must be registered.")
    public void login(@RequestBody AuthCredentials authCredentials) {
        //TODO: It is working correctly by delegating calls to security login.
    }
}
