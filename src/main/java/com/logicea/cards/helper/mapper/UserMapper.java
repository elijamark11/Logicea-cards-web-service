package com.logicea.cards.helper.mapper;

import com.logicea.cards.dtos.auth.UserDTO;
import com.logicea.cards.entities.users.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO createDTO(User user);

    User createUser(UserDTO user);
}