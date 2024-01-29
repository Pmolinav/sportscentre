package net.pmolinav.bookings.mapper;

import net.pmolinav.bookings.dto.UserDTO;
import net.pmolinav.bookings.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDTOToUserEntity(UserDTO userDTO);

    UserDTO userEntityTOUserDTO(User user);

}
