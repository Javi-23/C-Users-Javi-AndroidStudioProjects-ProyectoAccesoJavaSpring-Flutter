package com.vedruna.controllers;

import com.vedruna.dto.CommentsDTO;
import com.vedruna.services.CommentsServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/users/posts/comments")
@Tag(name = "Comentarios", description = "Operaciones relacionadas con los comentarios")
public class CommentsController {

    private final CommentsServiceI commentsServiceI;

    @Autowired
    public CommentsController(CommentsServiceI commentsServiceI) {
        this.commentsServiceI = commentsServiceI;
    }

    @PostMapping("/add/{postId}")
    @Operation(summary = "Crear Comentario")
    public ResponseEntity<CommentsDTO> addComment(
            @PathVariable Long postId,
            @RequestParam(value = "text") String text,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CommentsDTO newComment = commentsServiceI.addComment(postId, username, text, image);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
}