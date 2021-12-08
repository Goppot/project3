package com.amr.project.converter;

import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" ,uses = AddressMapper.class)
public interface UserMapper {
    UserDto userToDto(User user);

    User dtoToUser(UserDto userDto);
}
