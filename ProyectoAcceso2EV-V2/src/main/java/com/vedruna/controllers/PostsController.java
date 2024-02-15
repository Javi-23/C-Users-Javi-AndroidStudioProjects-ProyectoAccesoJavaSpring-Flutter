package com.vedruna.controllers;

import com.vedruna.dto.PostDTO;
import com.vedruna.services.PostServiceI;
import com.vedruna.services.UserServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Publicaciones", description = "Operaciones relacionadas con Publicaciones")
@SecurityRequirement(name = "bearerAuth")
public class PostsController {
    @Autowired
    private final PostServiceI postService;

    @PostMapping("/create")
    @Operation(summary = "Crear Publicación")
    public ResponseEntity<PostDTO> createPost(
            @RequestParam(value = "text") String text,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Authentication authentication
    ) {
        String username = authentication.getName();
        PostDTO createdPost = postService.createPost(username, text, image);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/update/{postId}")
    @Operation(summary = "Actualizar Publicación")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long postId,
            @RequestParam(value = "text") String text,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Authentication authentication
    ) {
        String username = authentication.getName();
        PostDTO updatedPost = postService.updatePost(postId, username, text, image);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "Borrar Publicación")
    public ResponseEntity<String> deletePost(
            @PathVariable Long postId,
            Authentication authentication) {

        String username = authentication.getName();
        postService.deletePost(postId, username);

        return ResponseEntity.ok("Publicación eliminada correctamente");
    }

}

