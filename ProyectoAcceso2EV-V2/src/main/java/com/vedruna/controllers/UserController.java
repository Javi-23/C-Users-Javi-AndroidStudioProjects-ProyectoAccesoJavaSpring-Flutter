package com.vedruna.controllers;

import com.vedruna.dto.UserDTO;
import com.vedruna.services.UserServiceI;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los Usuarios")
public class UserController {
    @Autowired
    private final UserServiceI userService;

    @GetMapping("/public/{username}")
    @Operation(summary = "Obtener usuario por username")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
        UserDTO userDTO = userService.findUserByUsernameDTO(username);
        return ResponseEntity.ok(userDTO);

    }

    @PutMapping("/update-description")
    @Operation(summary = "Editar descripción del usuario")
    public ResponseEntity<UserDTO> editUserDescription(@RequestBody UserDTO userUpdateDTO, Authentication authentication) {
        String username = authentication.getName();
        UserDTO updatedUser = userService.updateUserDescription(username, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/followers/{username}")
    @Operation(summary = "Obtener los seguidores de un usuario")
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable String username) {
        List<UserDTO> followers = userService.getFollowers(username);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/follows/{username}")
    @Operation(summary = "Obtener los seguidos de un usuario")
    public ResponseEntity<List<UserDTO>> getFollows(@PathVariable String username) {
        List<UserDTO> followers = userService.getFollows(username);
        return ResponseEntity.ok(followers);
    }
}

