package com.zys.elec.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.PostDTO;
import com.zys.elec.entity.Post;
import com.zys.elec.entity.User;
import com.zys.elec.repository.FollowRepository;
import com.zys.elec.repository.PostRepository;
import com.zys.elec.repository.UserRepository;
import com.zys.elec.service.FollowService;
import com.zys.elec.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

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

    @Override
    public ServiceResult<List<Post>> getPostsByUserId(Long userId) {
        var userExists = userRepository.findById(userId);
        if (userExists.isEmpty()) {
            return ServiceResult.failure("User not found");
        }

        var postsOpt = postRepository.findByUserAndIsDeletedFalse(userExists.get());
        return postsOpt.map(posts -> {
            posts.forEach(post -> post.setUser(null));
            return ServiceResult.success(posts);
        }).orElseGet(() -> ServiceResult.failure("No posts found for the user"));

    }

    @Override
    public ServiceResult<List<PostDTO>> getPosts(boolean onlyFriends, Long userId) {
        var userExists = userRepository.findById(userId);
        if (userExists.isEmpty()) {
            return ServiceResult.failure("User not found");
        }

        var user = userExists.get();

        if (onlyFriends) {
            var followerOpt = followRepository.findByFollower(user);
            var followeeOpt = followRepository.findByFollowee(user);

            if (followerOpt.isEmpty() || followeeOpt.isEmpty()) {
                return ServiceResult.failure("No friends found for the user");
            }

            var follower = followerOpt.get();
            var followee = followeeOpt.get();
            var friends = new HashSet<User>();
            follower.forEach(f -> friends.add(f.getFollowee()));
            followee.forEach(f -> friends.add(f.getFollower()));
            followee.forEach(f -> friends.add(f.getFollower()));
            var friendsList = new ArrayList<User>(friends);

            var postsOpt = postRepository.findByUserInAndIsDeletedFalse(friendsList);
            var postsDTO = new ArrayList<PostDTO>();
            postsOpt.ifPresent(posts -> {
                posts.forEach(post -> {
                    postsDTO.add(PostDTO.fromEntity(post));
                });
            });
            return ServiceResult.success(postsDTO);
        } else {
            var postsOpt = postRepository.findByIsDeletedFalse();
            var postsDTO = new ArrayList<PostDTO>();
            postsOpt.ifPresent(posts -> {
                posts.forEach(post -> {
                    postsDTO.add(PostDTO.fromEntity(post));
                });
            });
                return ServiceResult.success(postsDTO);
        }
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