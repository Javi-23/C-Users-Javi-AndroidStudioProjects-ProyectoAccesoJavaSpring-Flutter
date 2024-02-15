package com.vedruna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vedruna.persistence.model.Comments;
import com.vedruna.persistence.model.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    private LocalDateTime creationDate;
    //private List<Posts> posts;
}

