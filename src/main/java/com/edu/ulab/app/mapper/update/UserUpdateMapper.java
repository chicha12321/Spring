package com.edu.ulab.app.mapper.update;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.web.response.update.UserRequestUpdate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserUpdateMapper {
    UserDto userRequestUpdateToUserDto(UserRequestUpdate userRequestUpdate);

    UserRequestUpdate userDtoToUserRequestUpdate(UserDto userDto);
}
