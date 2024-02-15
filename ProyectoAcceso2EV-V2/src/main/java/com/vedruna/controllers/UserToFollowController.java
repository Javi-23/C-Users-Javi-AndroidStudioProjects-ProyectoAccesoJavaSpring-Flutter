package com.vedruna.controllers;

import com.vedruna.services.UserToFollowI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Usuarios a seguir", description = "Operaciones relacionadas con los comentarios")
public class UserToFollowController {
    @Autowired
    private final UserToFollowI userToFollowServiceI;


    @PostMapping("/follow/{followedUsername}")
    @Operation(summary = "Seguir usuario")
    public ResponseEntity<Void> followUser(
            @PathVariable String followedUsername,
            Authentication authentication) {

        String followerUsername = authentication.getName();
        userToFollowServiceI.followUser(followerUsername, followedUsername);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/unfollow/{followedUsername}")
    @Operation(summary = "Dejar de seguir usuario")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable String followedUsername,
            Authentication authentication) {

        String followerUsername = authentication.getName();
        userToFollowServiceI.unfollowUser(followerUsername, followedUsername);

        return ResponseEntity.ok().build();
    }


}
