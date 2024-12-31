package com.zys.elec.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "predicts")
@Data
public class Predict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strategy", nullable = false)
    private String strategy; // 预测策略，如LSTM或GRU

    @Column(name = "predicted_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime predictedAt; // 做出预测的时间

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate; // 预测目标数据的日期

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 被预测的目标数据所属的用户

    @ManyToOne
    @JoinColumn(name = "electricity_record_id", nullable = true)
    private ElectricityRecord electricityRecord; // 预测的电力记录，可以为空

    @Column(name = "predicted_value", nullable = true)
    private BigDecimal predictedValue; // 预测的电力消耗值


    // 其他字段和方法
}