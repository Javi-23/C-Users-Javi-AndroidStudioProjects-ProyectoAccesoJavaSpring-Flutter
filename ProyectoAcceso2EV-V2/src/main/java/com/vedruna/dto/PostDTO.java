package com.vedruna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vedruna.persistence.model.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private String text;
    private byte[] image;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    private LocalDateTime creationDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    private LocalDateTime editDate;
    private List<CommentsDTO> comments;
}
