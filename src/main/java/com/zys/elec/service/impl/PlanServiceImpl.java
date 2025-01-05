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
        if (!user.isSuccess()) {
            return ServiceResult.failure("User not found");
        }

        var plans = planRepository.findByUserId(userId);
        return ServiceResult.success(plans.orElse(null));
    }

    @Override
    public ServiceResult<Void> deletePlan(Long planId) {
        var plan = planRepository.findById(planId);
        if (plan.isEmpty()) {
            return ServiceResult.failure("Plan not found");
        }

        try {
            planRepository.deleteById(planId);
            return ServiceResult.success(null);
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @Override
    public ServiceResult<Void> cancelPlan(Long planId, Long userId) {
        if (!userService.getUserById(userId).isSuccess()) {
            return ServiceResult.failure("User not found");
        }

        var plan = planRepository.findById(planId);
        if(plan.isEmpty()) {
            return ServiceResult.failure("Plan not found");
        }

        if(plan.get().getUserId() != userId) {
            return ServiceResult.failure("User does not own the plan");
        }

        try {
            var p = plan.get();
            p.setStatus(Plan.Status.CANCELLED);
            planRepository.save(p);
            return ServiceResult.success(null);
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
        

    }

    @Override
    public ServiceResult<Void> completePlan(Long planId) {
        var plan = planRepository.findById(planId);
        if(plan.isEmpty()) {
            return ServiceResult.failure("Plan not found");
        }

        try {
            var p = plan.get();
            p.setStatus(Plan.Status.COMPLETED);
            planRepository.save(p);
            return ServiceResult.success(null);
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    @Override
    public ServiceResult<Void> updatePlanContent(Plan plan) {
        var p = planRepository.findById(plan.getId());
        if(p.isEmpty()) {
            return ServiceResult.failure("Plan not found");
        }

        try {
            planRepository.save(plan);
            return ServiceResult.success(null);
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

}
