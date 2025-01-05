package com.zys.elec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.UserDTO;
import com.zys.elec.entity.User;
import com.zys.elec.service.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    // post subscription
    @PostMapping("/follow")
    @ResponseBody
    public ResponseResult<Void> followUser(@RequestParam Long followerId, @RequestParam Long followeeId) {

        var result = followService.followUser(followerId, followeeId);
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

        var result = followService.unfollowUser(followerId, followeeId);
        if (result.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

    @GetMapping("/getfollowers")
    @ResponseBody
    public ResponseResult<List<UserDTO>> getFollowers(@RequestParam Long userId) {
        var result = followService.getFollowers(userId);

        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

    @GetMapping("/getfollowees")
    @ResponseBody
    public ResponseResult<List<UserDTO>> getFollowees(@RequestParam Long userId) {
        var result = followService.getFollowees(userId);
        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }
}
