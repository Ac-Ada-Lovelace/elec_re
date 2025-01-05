package com.zys.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Plan;
import com.zys.elec.repository.PlanRepository;
import com.zys.elec.service.PlanService;
import com.zys.elec.service.UserService;

public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserService userService;

    @Override
    public ServiceResult<Void> createPlan(Plan plan) {
        var user = userService.getUserById(plan.getUserId());

        if (user == null) {
            return ServiceResult.failure("User not found");
        }

        try {

            planRepository.save(plan);
            return ServiceResult.success(null);
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }

    }



    @Override
    public com.zys.elec.common.ServiceResult<List<Plan>> listPlans(Long userId) {
        var user = userService.getUserById(userId);
        if(!user.isSuccess()) {
            return ServiceResult.failure("User not found");
        }

        var plans = planRepository.findByUserId(userId);
        
        return ServiceResult.success(plans);
    }



    @Override
    public ServiceResult<Void> updatePlanContent(Long planId, String newContent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePlanContent'");
    }



    @Override
    public ServiceResult<Void> deletePlan(Long planId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePlan'");
    }

}
