package com.vedruna.services;

import com.vedruna.persistence.model.Repository.UserRepository;
import com.vedruna.persistence.model.Repository.UserToFollowRepository;
import com.vedruna.persistence.model.User;
import com.vedruna.persistence.model.UserToFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserToFollowImpl implements UserToFollowI {

    private final UserToFollowRepository userToFollowRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserToFollowImpl(UserToFollowRepository userToFollowRepository, UserRepository userRepository) {
        this.userToFollowRepository = userToFollowRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void followUser(String followerUsername, String followedUsername) {
        if (followerUsername.equals(followedUsername)) {
            throw new IllegalArgumentException("No puedes seguirte a ti mismo");
        }

        User follower = getUserByUsername(followerUsername);
        User followed = getUserByUsername(followedUsername);

        if (!isFollowed(follower, followed)) {
            UserToFollow userToFollow = new UserToFollow();
            userToFollow.setFollower(follower);
            userToFollow.setFollowed(followed);
            userToFollowRepository.save(userToFollow);
        }
    }

    @Override
    public void unfollowUser(String followerUsername, String followedUsername) {
        User follower = getUserByUsername(followerUsername);
        User followed = getUserByUsername(followedUsername);

        userToFollowRepository.deleteByFollowerUsernameAndFollowedUsername(followerUsername, followedUsername);
    }

    private boolean isFollowed(User follower, User followed) {
        return userToFollowRepository.existsByFollowerAndFollowed(follower, followed);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));
    }
}