package com.zys.elec.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

// ### **关注关系表 (`follows`)**

// - **描述**: 描述用户之间的关注关系。
// - **字段**:
//   - `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT): 关系ID。
//   - `follower_id` (BIGINT, NOT NULL): 关注者用户ID。
//   - `followee_id` (BIGINT, NOT NULL): 被关注用户ID。
//   - `created_at` (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP): 关注时间。
//   - `is_deleted` (BOOLEAN, DEFAULT FALSE): 是否逻辑删除。

// ---

@Entity
@Table(name = "follows")
@Data
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
    }
}