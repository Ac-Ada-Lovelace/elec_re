package com.zys.elec.dto;

import java.time.LocalDateTime;

import com.zys.elec.entity.Post;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;

    private Long userId;

    private String UserName;


    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isDeleted;

    public static PostDTO fromEntity(Post post) {
        var postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setUserName(post.getUser().getUsername());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdatedAt(post.getUpdatedAt());
        postDTO.setDeleted(post.isDeleted());

        return postDTO;
    }
}
