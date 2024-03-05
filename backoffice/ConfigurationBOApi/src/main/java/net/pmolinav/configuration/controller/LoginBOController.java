package net.pmolinav.configuration.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.pmolinav.configuration.security.AuthCredentials;
import net.pmolinav.configuration.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//TODO: ORDER SWAGGER WITHOUT USING NUMBERS IN TAGS TO PUT LOGIN AT FIRST.
@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("login")
@Tag(name = "1. Login", description = "The Login Controller. Required to authorize users. A valid token is granted and allows valid users to call other controllers with permissions.")
public class LoginBOController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping()
    @Operation(summary = "Authorize user", description = "This is a public endpoint. Authentication is not required to call, but requested user must be registered.")
    public ResponseEntity<?> login(@RequestBody @Valid AuthCredentials request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + TokenUtils.createToken(request.getUsername(), request.getPassword(), null))
                    .build();
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
