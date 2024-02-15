package com.vedruna.services;

import com.vedruna.dto.CommentsDTO;
import com.vedruna.dto.PostDTO;
import com.vedruna.dto.UserAndPostDTO;
import com.vedruna.persistence.model.Comments;
import com.vedruna.persistence.model.Posts;
import com.vedruna.persistence.model.Repository.PostsRepository;
import com.vedruna.persistence.model.Repository.UserRepository;
import com.vedruna.persistence.model.Repository.UserToFollowRepository;
import com.vedruna.persistence.model.User;
import com.vedruna.persistence.model.UserToFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAndPostServiceImpl implements  UserAndPostServiceI{

    private final UserRepository userRepository;
    private final PostsRepository postRepository;
    private final UserToFollowRepository userToFollowRepository;

    @Autowired
    public UserAndPostServiceImpl(UserRepository userRepository, PostsRepository postRepository, UserToFollowRepository userToFollowRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.userToFollowRepository = userToFollowRepository;
    }

    @Override
    public UserAndPostDTO getPostByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));

        List<Posts> userPosts = postRepository.findByAuthorId(user);

        return convertToDto(user, userPosts);
    }

    @Override
    public List<UserAndPostDTO> getPostsOfFollowedUsers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));

        List<User> followedUsers = getFollowedUsers(user);

        List<UserAndPostDTO> userAndPostDTOs = new ArrayList<>();

        for (User followedUser : followedUsers) {
            List<Posts> userPosts = postRepository.findByAuthorId(followedUser);

            List<PostDTO> postDTOs = userPosts.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            UserAndPostDTO userAndPostDTO = new UserAndPostDTO();
            userAndPostDTO.setId(followedUser.getId());
            userAndPostDTO.setUsername(followedUser.getUsername());
            userAndPostDTO.setDescription(followedUser.getDescription());
            userAndPostDTO.setPosts(postDTOs);

            userAndPostDTOs.add(userAndPostDTO);
        }

        return userAndPostDTOs;
    }

    @Override
    public List<UserAndPostDTO> getAllPosts() {
        List<Posts> allPosts = postRepository.findAll();
        return allPosts.stream()
                .map(post -> convertToDto(post.getAuthorId(), List.of(post)))
                .collect(Collectors.toList());
    }

    private List<User> getFollowedUsers(User user) {
        List<UserToFollow> followedRelationships = userToFollowRepository.findByFollower(user);

        return followedRelationships.stream()
                .map(UserToFollow::getFollowed)
                .collect(Collectors.toList());
    }
    private UserAndPostDTO convertToDto(User user, List<Posts> posts) {
        UserAndPostDTO userAndPostDTO = new UserAndPostDTO();
        userAndPostDTO.setId(user.getId());
        userAndPostDTO.setUsername(user.getUsername());
        userAndPostDTO.setDescription(user.getDescription());

        List<PostDTO> postDTOs = posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        userAndPostDTO.setPosts(postDTOs);

        return userAndPostDTO;
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
        commentsDTO.setImage(comment.getImage());
        commentsDTO.setCreationDate(comment.getCreationDate());
        return commentsDTO;
    }
}
