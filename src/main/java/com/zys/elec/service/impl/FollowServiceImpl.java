package com.zys.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Follow;
import com.zys.elec.entity.User;
import com.zys.elec.service.FollowService;
import com.zys.elec.service.UserService;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private UserService userService;

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

}
