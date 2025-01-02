package com.zys.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.Follow;
import com.zys.elec.entity.User;
import com.zys.elec.repository.FollowRepository;
import com.zys.elec.repository.UserRepository;
import com.zys.elec.service.FollowService;
import com.zys.elec.service.UserService;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ServiceResult<Follow> followUser(User follower, User followee) {
        if (!userService.getUserById(follower.getId()).isSuccess()) {
            return ServiceResult.failure("Follower does not exist");
        }

        if (!userService.getUserById(followee.getId()).isSuccess()) {
            return ServiceResult.failure("Followee does not exist");
        }

        if (isFollowing(follower, followee).isSuccess()) {
            return ServiceResult.failure("Already following");
        }

        ServiceResult<Follow> followResult = followUser(follower, followee);
        if (followResult.isSuccess()) {
            return ServiceResult.success(followResult.getData());
        } else {
            return ServiceResult.failure("Failed to follow user");
        }

    }

    @Override
    public ServiceResult<Void> unfollowUser(User follower, User followee) {
        if (!userService.getUserById(follower.getId()).isSuccess()) {
            return ServiceResult.failure("Follower does not exist");
        }

        if (!userService.getUserById(followee.getId()).isSuccess()) {
            return ServiceResult.failure("Followee does not exist");
        }

        if (!isFollowing(follower, followee).isSuccess()) {
            return ServiceResult.failure("Not following");
        }

        ServiceResult<Void> unfollowResult = unfollowUser(follower, followee);
        if (unfollowResult.isSuccess()) {
            return ServiceResult.success(null);
        } else {
            return ServiceResult.failure("Failed to unfollow user");
        }
    }

    @Override
    public ServiceResult<List<Follow>> getFollowers(User followee) {
        var followers = getFollowers(followee);
        if (followers.isSuccess()) {
            return ServiceResult.success(followers.getData());
        } else {
            return ServiceResult.failure("Failed to get followers");
        }
    }

    @Override
    public ServiceResult<List<Follow>> getFollowees(User follower) {
        var followees = getFollowees(follower);
        if (followees.isSuccess()) {
            return ServiceResult.success(followees.getData());
        } else {
            return ServiceResult.failure("Failed to get followees");
        }
    }

    @Override
    public ServiceResult<Boolean> isFollowing(User follower, User followee) {
        var isFollowing = isFollowing(follower, followee);
        if (isFollowing.isSuccess()) {
            return ServiceResult.success(isFollowing.getData());
        } else {
            return ServiceResult.failure("Failed to check if following");
        }
    }

    @Override
    public ServiceResult<List<UserDTO>> getFollowers(long userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ServiceResult.failure("User not found");
        }
        var res = followRepository.findByFollowee(user.get());
        // get followers
        var followers = res.get().stream().map(f -> f.getFollower()).toList();
        var followersDTO = followers.stream().map(UserDTO::fromEntity).toList();

        return ServiceResult.success(followersDTO);
    }

}
