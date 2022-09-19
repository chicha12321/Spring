package com.edu.ulab.app.mapper.dto;


import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
