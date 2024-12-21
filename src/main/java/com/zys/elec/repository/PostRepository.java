package com.zys.elec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zys.elec.entity.Post;
import com.zys.elec.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findByUser(User user);

    Optional<List<Post>> findByUserAndIsDeletedFalse(User user);

    Optional<List<Post>> findByIsDeletedFalse();

    Optional<Post> findByIdAndIsDeletedFalse(Long id);
}
