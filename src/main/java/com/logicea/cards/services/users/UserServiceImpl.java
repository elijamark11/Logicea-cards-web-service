package com.logicea.cards.services.users;

import com.logicea.cards.dtos.auth.UserDTO;
import com.logicea.cards.helper.ResponseStatus;
import com.logicea.cards.helper.dto.APIResponse;
import com.logicea.cards.helper.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public APIResponse createUser(UserDTO userDTO) {
        var user = userMapper.createUser(userDTO);

        return new APIResponse<>(ResponseStatus.SUCCESS.getResponseCode(),
                ResponseStatus.SUCCESS.getDescription(), user);
    }
}