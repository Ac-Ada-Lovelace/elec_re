package com.zys.elec.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zys.elec.entity.Follow;
import com.zys.elec.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<List<Follow>> findByFollower(User follower);

    Optional<List<Follow>> findByFollowee(User followee);

}
