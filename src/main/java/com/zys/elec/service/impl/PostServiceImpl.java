package com.zys.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Post;
import com.zys.elec.entity.User;
import com.zys.elec.repository.PostRepository;
import com.zys.elec.repository.UserRepository;
import com.zys.elec.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ServiceResult<Post> createPost(Post post) {
        try {
            Post savedPost = postRepository.save(post);
            return ServiceResult.success(savedPost);
        } catch (Exception e) {
            return ServiceResult.failure("Failed to create post: " + e.getMessage());
        }
    }

    @Override
    public ServiceResult<Void> updatePostContent(Long postId, String newContent) {
        var postOpt = postRepository.findByIdAndIsDeletedFalse(postId);
        if (postOpt.isEmpty()) {
            return ServiceResult.failure("Post not found or already deleted");
        }

        var post = postOpt.get();
        post.setContent(newContent);
        postRepository.save(post);
        return ServiceResult.success(null);
    }

    @Override
    public ServiceResult<Void> deletePost(Long postId) {
        var postOpt = postRepository.findByIdAndIsDeletedFalse(postId);
        if (postOpt.isEmpty()) {
            return ServiceResult.failure("Post not found or already deleted");
        }

        var post = postOpt.get();
        post.setDeleted(true);
        postRepository.save(post);
        return ServiceResult.success(null);
    }

    @Override
    public ServiceResult<List<Post>> getUserPosts(User user) {
        try {
            var postsOpt = postRepository.findByUserAndIsDeletedFalse(user);
            return postsOpt.map(ServiceResult::success)
                    .orElseGet(() -> ServiceResult.failure("No posts found for the user"));
        } catch (Exception e) {
            return ServiceResult.failure("Failed to fetch posts: " + e.getMessage());
        }
    }

    @Override
    public ServiceResult<Post> getPostById(Long postId) {
        var postOpt = postRepository.findByIdAndIsDeletedFalse(postId);
        if (postOpt.isPresent()) {
            return ServiceResult.success(postOpt.get());
        } else {
            return ServiceResult.failure("Post not found or already deleted");
        }
    }

    @Override
    public ServiceResult<Post> createPost(Long userId, String content) {
        var userExists = userRepository.findById(userId);
        if (userExists.isEmpty()) {
            return ServiceResult.failure("User not found");
        }

        var post = new Post();
        post.setContent(content);
        post.setUser(userExists.get());
        

        try {
            postRepository.save(post);
        } catch (Exception e) {
            return ServiceResult.failure("Failed to create post: " + e.getMessage());
        }
        return ServiceResult.success(post);

    }

    // @Override
    // public ServiceResult<Void> likePost(Long postId) {
    // var postOpt = postRepository.findByIdAndIsDeletedFalse(postId);
    // if (postOpt.isEmpty()) {
    // return ServiceResult.failure("Post not found or already deleted");
    // }
    //
    // var post = postOpt.get();
    // post.setLikes(post.getLikes() + 1);
    // postRepository.save(post);
    // return ServiceResult.success(null);
    // }
    //
    // @Override
    // public ServiceResult<Void> forwardPost(Long postId) {
    // var postOpt = postRepository.findByIdAndIsDeletedFalse(postId);
    // if (postOpt.isEmpty()) {
    // return ServiceResult.failure("Post not found or already deleted");
    // }
    //
    // var post = postOpt.get();
    // post.setForwards(post.getForwards() + 1);
    // postRepository.save(post);
    // return ServiceResult.success(null);
    // }
}
