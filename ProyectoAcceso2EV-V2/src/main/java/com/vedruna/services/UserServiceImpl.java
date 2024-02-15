package com.vedruna.services;

import com.vedruna.dto.UserDTO;
import com.vedruna.persistence.model.Repository.UserToFollowRepository;
import com.vedruna.persistence.model.User;
import com.vedruna.persistence.model.Repository.UserRepository;
import com.vedruna.persistence.model.UserToFollow;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI{

    private final UserRepository userRepository;
    private final UserToFollowRepository userToFollowRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserToFollowRepository userToFollowRepository) {
        this.userRepository = userRepository;
        this.userToFollowRepository = userToFollowRepository;
    }

    @Override
    public UserDTO findUserByUsernameDTO(String username) {
        User user = findUserByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setDescription(user.getDescription());
        userDTO.setCreationDate(user.getCreationDate());
        //userDTO.setPosts(user.getPosts());
        return userDTO;
    }


    @Override
    public UserDTO updateUserDescription(String username, UserDTO userUpdateDTO) {
        User user = findUserByUsername(username);
        if (userUpdateDTO.getDescription() != null) {
            user.setDescription(userUpdateDTO.getDescription());
        }
        User updatedUser = userRepository.save(user);

        return convertToDto(updatedUser);
    }


    @Override
    public List<UserDTO> getFollowers(String username) {
        User user = findUserByUsername(username);

        List<UserToFollow> userToFollowList = userToFollowRepository.findByFollowed(user);

        List<UserDTO> followers = userToFollowList.stream()
                .map(UserToFollow::getFollower)
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return followers;
    }

    @Override
    public List<UserDTO> getFollows(String username) {
        User user = findUserByUsername(username);

        List<UserToFollow> userToFollowList = userToFollowRepository.findByFollower(user);

        List<UserDTO> follows = userToFollowList.stream()
                .map(UserToFollow::getFollowed)
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return follows;
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    private UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
