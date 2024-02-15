package com.vedruna.services;

import com.vedruna.dto.CommentsDTO;
import com.vedruna.persistence.model.Comments;
import com.vedruna.persistence.model.Posts;
import com.vedruna.persistence.model.Repository.CommentsRepository;
import com.vedruna.persistence.model.Repository.PostsRepository;
import com.vedruna.persistence.model.Repository.UserRepository;
import com.vedruna.persistence.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CommentsServiceImpl implements  CommentsServiceI{

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentsServiceImpl(CommentsRepository commentsRepository, PostsRepository postsRepository, UserRepository userRepository) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CommentsDTO addComment(Long postId, String authorId, String commentText, MultipartFile image) {
        User author = userRepository.findByUsername(authorId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + authorId));

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Publicación no encontrada con el ID: " + postId));

        Comments newComment = new Comments();
        newComment.setAuthorId(author);
        newComment.setPostId(post);
        newComment.setComment(commentText);

        if (image != null && !image.isEmpty()) {
            try {
                newComment.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen", e);
            }
        }

        Comments savedComment = commentsRepository.save(newComment);
        return convertToDTO(savedComment);
    }

    private CommentsDTO convertToDTO(Comments comment) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setId(comment.getId());
        commentsDTO.setComment(comment.getComment());
        commentsDTO.setCreationDate(comment.getCreationDate());
        return commentsDTO;
    }
}
