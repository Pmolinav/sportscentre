package net.pmolinav.bookingslib.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    @NotBlank(message = "Username is mandatory.")
    private String username;

    @NotBlank(message = "Password is mandatory.")
    private String password;

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotBlank(message = "Email is mandatory.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotNull(message = "Role is mandatory.")
    private Role role;

    @NotNull(message = "Creation date is mandatory.")
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") // Date format
    private Date creationDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") // Date format
    private Date modificationDate;

}
