package com.logicea.cards.services.users;

import com.logicea.cards.dtos.auth.UserDTO;
import com.logicea.cards.helper.dto.APIResponse;

public interface UserService {
    APIResponse createUser(UserDTO userDTO);
}