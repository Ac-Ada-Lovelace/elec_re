package com.zys.elec.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
 * ### **用户表 (`users`)**

- **描述**: 记录用户的基本信息。
- **字段**:
  - `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT): 用户ID。
  - `username` (VARCHAR(255), UNIQUE, NOT NULL): 用户名。
  - `password` (VARCHAR(255), NOT NULL): 密码。
  - `avatar` (BLOB, NULL): 头像数据。
  - `created_at` (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP): 用户创建时间。
  - `is_deleted` (BOOLEAN, DEFAULT FALSE): 是否逻辑删除。

 */

@Entity
@Data
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)

  private String password;

  @Column(name = "avatar")
  private byte[] avatar;

  @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime updatedAt;

  @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean isDeleted;


  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ElectricityRecord> electricityRecords;
}
