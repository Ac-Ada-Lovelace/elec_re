package com.zys.elec.dto;

import com.zys.elec.entity.Plan;

import lombok.Data;

@Data
public class PlanDTO {
    
    private Long id;

    private Long userId;

    private String startTime;

    private String endTime;

    
    private String actualEndTime;
    
    private String updateAt;
    
    
    private String targetEc;
    
    private String currentEc;
    
    
    
    private Plan.Status status;
    
    private Double progress;
}
