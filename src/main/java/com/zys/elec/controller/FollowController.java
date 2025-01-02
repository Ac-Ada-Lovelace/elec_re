package com.zys.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.entity.User;
import com.zys.elec.service.FollowService;

public class FollowController {

    @Autowired
    private FollowService followService;

    // post subscription
    @PostMapping("/follow")
    @ResponseBody
    public ResponseResult<Void> followUser(@RequestParam Long followerId, @RequestParam Long followeeId) {
        var follower = new User();
        follower.setId(followerId);
        var followee = new User();
        followee.setId(followeeId);
        var result = followService.followUser(follower, followee);
        if (result.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

    // post unsubscription
    @PostMapping("/unfollow")
    @ResponseBody
    public ResponseResult<Void> unfollowUser(@RequestParam Long followerId, @RequestParam Long followeeId) {
        var follower = new User();
        follower.setId(followerId);
        var followee = new User();
        followee.setId(followeeId);
        var result = followService.unfollowUser(follower, followee);
        if (result.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

    @GetMapping("/followers")
    @ResponseBody
    public ResponseResult<List<UserDTO>> getFollowersCount(@RequestParam Long userId) {
        var result = followService.getFollowees();
        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }
}
