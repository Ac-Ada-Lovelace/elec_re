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
import com.zys.elec.dto.PostDTO;
import com.zys.elec.entity.Post;
import com.zys.elec.repository.PostRepository;
import com.zys.elec.service.PostService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @PostMapping("/create")
    @ResponseBody
    public ResponseResult<Post> createPost(
            @RequestParam Long userId,
            @RequestParam String content) {
        var result = postService.createPost(userId, content);
        if (result.isSuccess()) {
            var post = result.getData();
            post.setUser(null);
            return ResponseResult.success(post);
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
    public ResponseResult<List<Post>> listPosts(@RequestParam Long userId) {
        var result = postService.getPostsByUserId(userId);

        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

    @GetMapping("/getall")
    public ResponseResult<List<PostDTO>> postMethodName(@RequestParam String onlyFriends, @RequestParam Long userId) {
        var res = postService.getPosts(Boolean.parseBoolean(onlyFriends), userId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());
        }



    }
    

}

