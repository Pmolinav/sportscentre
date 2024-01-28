package net.pmolinav.bookings.mapper;

import net.pmolinav.bookings.dto.UserDTO;
import net.pmolinav.bookings.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDTOToUserEntity(UserDTO userDTO);

    UserDTO userEntityTOUserDTO(User user);

}
