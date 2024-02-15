package com.vedruna.persistence.model.Repository;


import com.vedruna.persistence.model.User;
import com.vedruna.persistence.model.UserToFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserToFollowRepository extends JpaRepository<UserToFollow, Long> {
    @Transactional
    void deleteByFollowerUsernameAndFollowedUsername(String followerUsername, String followedUsername);
    boolean existsByFollowerAndFollowed(User follower, User followed);

    List<UserToFollow> findByFollowed(User followed);
    List<UserToFollow> findByFollower(User followed);
}
