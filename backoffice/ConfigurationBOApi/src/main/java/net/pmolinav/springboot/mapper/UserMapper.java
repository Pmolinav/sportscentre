package net.pmolinav.springboot.mapper;

import net.pmolinav.springboot.dto.UserDTO;
import net.pmolinav.springboot.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDTOToUserEntity(UserDTO userDTO);

    UserDTO userEntityTOUserDTO(User user);

}
