package net.pmolinav.configuration.security;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthCredentials {
    @NotNull
    private String username;
    @NotNull
    private String password;

}
