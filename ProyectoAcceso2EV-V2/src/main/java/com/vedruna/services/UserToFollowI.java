package com.vedruna.services;

public interface UserToFollowI {
    void followUser(String followerUsername, String followedUsername);
    void unfollowUser(String followerUsername, String followedUsername);

}
