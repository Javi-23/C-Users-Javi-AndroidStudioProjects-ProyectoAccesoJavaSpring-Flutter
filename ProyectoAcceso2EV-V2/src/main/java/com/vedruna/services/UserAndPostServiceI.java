package com.vedruna.services;

import com.vedruna.dto.UserAndPostDTO;

import java.util.List;

public interface UserAndPostServiceI {
    UserAndPostDTO getPostByUserName(String username);
    List<UserAndPostDTO> getPostsOfFollowedUsers(String username);

    List<UserAndPostDTO> getAllPosts();
}
