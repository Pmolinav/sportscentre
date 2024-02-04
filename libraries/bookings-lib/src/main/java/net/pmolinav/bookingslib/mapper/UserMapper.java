package net.pmolinav.bookingslib.mapper;

import net.pmolinav.bookingslib.dto.UserDTO;
import net.pmolinav.bookingslib.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDTOToUserEntity(UserDTO userDTO);

    UserDTO userEntityTOUserDTO(User user);

}
