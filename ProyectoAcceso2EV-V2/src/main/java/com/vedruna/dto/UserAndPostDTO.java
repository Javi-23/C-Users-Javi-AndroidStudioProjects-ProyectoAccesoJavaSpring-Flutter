package com.vedruna.dto;

import com.vedruna.persistence.model.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAndPostDTO {
    private Long id;
    private String username;
    private String description;
    private List<PostDTO> posts;
}
