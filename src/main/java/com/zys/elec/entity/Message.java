package com.zys.elec.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * ### **私信表 (`messages`)**

- **描述**: 记录用户之间的私信内容。
- **字段**:
  - `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT): 私信ID。
  - `sender_id` (BIGINT, NOT NULL): 发送者用户ID。
  - `receiver_id` (BIGINT, NOT NULL): 接收者用户ID。
  - `content` (TEXT, NOT NULL): 私信内容。
  - `sent_at` (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP): 发送时间。
  - `is_read` (BOOLEAN, DEFAULT FALSE): 是否已读。
  - `is_deleted` (BOOLEAN, DEFAULT FALSE): 是否逻辑删除。
 */

@Entity
@Data
@Table(name = "message")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "sender_id", nullable = false)
  private Long senderId;

  @Column(name = "receiver_id", nullable = false)
  private Long receiverId;

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "sent_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime sentAt;

  @Column(name = "is_read", columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean isRead;

  @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean isDeleted;


}
