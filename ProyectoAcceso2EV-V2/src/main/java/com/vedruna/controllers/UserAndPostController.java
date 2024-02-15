package com.vedruna.controllers;

import com.vedruna.dto.UserAndPostDTO;
import com.vedruna.services.UserAndPostServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserAndPostController {

    private final UserAndPostServiceI userAndPostService;

    @Autowired
    public UserAndPostController(UserAndPostServiceI userAndPostService) {
        this.userAndPostService = userAndPostService;
    }

    @GetMapping("/public/posts/{username}")
    @Operation(summary = "Obtener publicaciones por username")
    public ResponseEntity<UserAndPostDTO> getUserAndPosts(@PathVariable String username) {
        UserAndPostDTO userAndPostDTO = userAndPostService.getPostByUserName(username);
        return ResponseEntity.ok(userAndPostDTO);
    }

    @GetMapping("/posts/followed")
    @Operation(summary = "Obtener publicaciones de los usuarios que sigo")
    public ResponseEntity<List<UserAndPostDTO>> getPostsOfFollowedUsers(Authentication authentication) {
        String username = authentication.getName();
        List<UserAndPostDTO> userAndPostDTOs = userAndPostService.getPostsOfFollowedUsers(username);
        return ResponseEntity.ok(userAndPostDTOs);
    }

    @GetMapping("/posts/all")
    @Operation(summary = "Obtener todas las publicaciones")
    public ResponseEntity<List<UserAndPostDTO>> getAllPosts() {
        List<UserAndPostDTO> userAndPostDTOS = userAndPostService.getAllPosts();
        return  ResponseEntity.ok(userAndPostDTOS);
    }
}
