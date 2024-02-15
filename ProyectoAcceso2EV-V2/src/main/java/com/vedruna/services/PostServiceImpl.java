package com.vedruna.services;

import com.vedruna.dto.CommentsDTO;
import com.vedruna.dto.PostDTO;
import com.vedruna.persistence.model.Comments;
import com.vedruna.persistence.model.Posts;
import com.vedruna.persistence.model.Repository.PostsRepository;
import com.vedruna.persistence.model.User;
import com.vedruna.persistence.model.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostServiceI{

    private final PostsRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostsRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDTO createPost(String username, String text, MultipartFile image) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));

        Posts post = new Posts();
        post.setText(text);
        post.setAuthorId(user);
        if (image != null && !image.isEmpty()) {
            try {
                post.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen", e);
            }
        }


        Posts savedPost = postRepository.save(post);

        return convertToDto(savedPost);
    }

    @Override
    public PostDTO updatePost(Long postId, String username, String text, MultipartFile image) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        if (!post.getAuthorId().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permisos para actualizar esta publicación");
        }
        post.setText(text);
        if (image != null && !image.isEmpty()) {
            try {
                post.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen", e);
            }
        }
        Posts updatedPost = postRepository.save(post);
        return convertToDto(updatedPost);
    }

    @Override
    public void deletePost(Long postId, String username) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Verificar que el usuario actual sea el autor de la publicación
        if (!post.getAuthorId().getUsername().equals(username)) {
            throw new RuntimeException("No estás autorizado para eliminar esta publicación");
        }
        // Eliminar la publicación
        postRepository.delete(post);
    }

    private PostDTO convertToDto(Posts post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setText(post.getText());
        postDTO.setCreationDate(post.getCreationDate());
        postDTO.setEditDate(post.getEditDate());
        List<CommentsDTO> commentsDTOList = Optional.ofNullable(post.getComments())
                .map(comments -> comments.stream().map(this::convertToDtoComments).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        postDTO.setComments(commentsDTOList);
        return postDTO;
    }
    private CommentsDTO convertToDtoComments(Comments comment) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setId(comment.getId());
        commentsDTO.setComment(comment.getComment());
        commentsDTO.setCreationDate(comment.getCreationDate());
        return commentsDTO;
    }

}
