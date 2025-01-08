package com.zys.elec.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "progress", nullable = false)
    private Double progress;

    @Column(name = "actual_end_time")
    private LocalDateTime actualEndTime;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;

    @Column(name = "update_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateAt;

    @Column(name = "target_ec", nullable = false)
    private Double targetEc;

    @Column(name = "current_ec", nullable = false)
    private Double currentEc;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        IN_PROGRESS,
        COMPLETED,
        FAILED,
        CANCELLED, OVERDUE
    }

    @PrePersist
    protected void onCreate() {
        if (title == null) {
            title = id.toString();
        }
        updateAt = LocalDateTime.now();
        status = Status.IN_PROGRESS;
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }
}