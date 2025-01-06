package com.zys.elec.service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Post;
import com.zys.elec.entity.User;

import java.util.List;

public interface PostService {

    ServiceResult<Post> createPost(Post post);
    ServiceResult<Post> createPost(Long userId, String content);

    ServiceResult<Void> updatePostContent(Long postId, String newContent);

    ServiceResult<Void> deletePost(Long postId);

    ServiceResult<List<Post>> getUserPosts(User user);

    ServiceResult<Post> getPostById(Long postId);

//    ServiceResult<Void> likePost(Long postId);
//
//    ServiceResult<Void> forwardPost(Long postId);
}
