package com.zys.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.entity.Post;
import com.zys.elec.service.PostService;

public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping("/post/create")
    @ResponseBody
    public ResponseResult<Post> createPost(
            @RequestParam Long userId,
            @RequestParam String content) {
        var post = new Post();
        post.getUser().setId(userId);
        post.setContent(content);
        var result = postService.createPost(post);
        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }


    @PostMapping("/post/update")
    @ResponseBody
    public ResponseResult<Void> updatePost(
            @RequestParam Long postId,
            @RequestParam String newContent) {
        var result = postService.updatePostContent(postId, newContent);

        if (result.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }


    @PostMapping("/post/delete")
    @ResponseBody
    public ResponseResult<Void> deletePost(@RequestParam Long postId) {
        var result = postService.deletePost(postId);

        if (result.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

    @PostMapping("/post/get")
    @ResponseBody
    public ResponseResult<Post> listPosts(@RequestParam Long userId) {
        var result = postService.getPostById(userId);

        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

//    // 点赞
//    @PostMapping("/post/like")
//    @ResponseBody
//    public ResponseResult<Void> likePost(@RequestParam Long postId) {
//        var result = postService.likePost(postId);
//
//        if (result.isSuccess()) {
//            return ResponseResult.success(null);
//        } else {
//            return ResponseResult.failure(result.getMessage());
//        }
//    }
//
//    // 转发
//    @PostMapping("/post/forward")
//    @ResponseBody
//    public ResponseResult<Void> forwardPost(@RequestParam Long postId) {
//        var result = postService.forwardPost(postId);
//
//        if (result.isSuccess()) {
//            return ResponseResult.success(null);
//        } else {
//            return ResponseResult.failure(result.getMessage());
//        }
//    }
}

