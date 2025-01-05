package com.zys.elec.service;

import java.util.List;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Plan;

public interface PlanService {
    ServiceResult<Void> createPlan(  Plan plan);
    
    ServiceResult<Void> updatePlanContent(Long planId, String newContent);

    ServiceResult<Void> deletePlan(Long planId);


    ServiceResult<List<Plan>> listPlans(Long userId);


}
