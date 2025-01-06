package com.zys.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.entity.Post;
import com.zys.elec.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping("/create")
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


    @PostMapping("/update")
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


    @PostMapping("/delete")
    @ResponseBody
    public ResponseResult<Void> deletePost(@RequestParam Long postId) {
        var result = postService.deletePost(postId);

        if (result.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

    @PostMapping("/get")
    @ResponseBody
    public ResponseResult<Post> listPosts(@RequestParam Long userId) {
        var result = postService.getPostById(userId);

        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

}

