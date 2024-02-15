package com.vedruna.services;

import com.vedruna.dto.UserDTO;

import java.util.List;

public interface UserServiceI {
    UserDTO findUserByUsernameDTO(String username);
    UserDTO updateUserDescription(String username, UserDTO userUpdateDTO);

    List<UserDTO> getFollowers(String username);
    List<UserDTO> getFollows(String username);
}
