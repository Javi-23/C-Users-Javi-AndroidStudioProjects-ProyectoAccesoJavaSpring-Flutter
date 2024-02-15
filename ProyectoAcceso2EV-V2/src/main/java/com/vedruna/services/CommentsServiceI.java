package com.vedruna.services;

import com.vedruna.dto.CommentsDTO;
import com.vedruna.persistence.model.Comments;
import org.springframework.web.multipart.MultipartFile;

public interface CommentsServiceI {
    CommentsDTO addComment(Long postId, String authorId, String commentText, MultipartFile image);
}
