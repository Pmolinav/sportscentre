package net.pmolinav.bookings.mapper;

import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDTOToUserEntity(UserDTO userDTO);

    UserDTO userEntityTOUserDTO(User user);

}
