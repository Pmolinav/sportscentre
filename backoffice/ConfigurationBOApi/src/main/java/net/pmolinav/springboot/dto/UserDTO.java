package net.pmolinav.springboot.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private Role role;
    private Date creationDate;
    private Date modificationDate;

}
