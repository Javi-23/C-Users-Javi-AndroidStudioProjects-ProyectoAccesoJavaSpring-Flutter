package com.vedruna.services;

import com.vedruna.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

public interface PostServiceI {
    PostDTO createPost(String username, String text, MultipartFile image);
    PostDTO updatePost(Long postId, String username, String text, MultipartFile image);
    void deletePost(Long postId, String username);
}
