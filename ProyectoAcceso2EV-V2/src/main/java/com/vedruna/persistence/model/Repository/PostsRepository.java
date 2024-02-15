package com.vedruna.persistence.model.Repository;

import com.vedruna.persistence.model.Posts;
import com.vedruna.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByAuthorId(User author);
}
