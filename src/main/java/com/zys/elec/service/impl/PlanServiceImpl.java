package com.zys.elec.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Plan;
import com.zys.elec.repository.ElectricityRecordRepository;
import com.zys.elec.repository.PlanRepository;
import com.zys.elec.repository.UserRepository;
import com.zys.elec.service.PlanService;
import com.zys.elec.service.UserService;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ElectricityRecordRepository electricityRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ServiceResult<Plan> createPlan(Plan plan) {
        var user = userService.getUserById(plan.getUserId());

        if (user == null) {
            return ServiceResult.failure("User not found");
        }

        plan.setCurrentEc(0.0);
        plan.setStatus(Plan.Status.IN_PROGRESS);
        plan.setProgress(0.0);
        plan.setDeleted(false);

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

        if (!plans.isEmpty()) {
            plans.get().forEach(x -> updatePlanStatus(x));
        }
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
        if (plan.isEmpty()) {
            return ServiceResult.failure("Plan not found");
        }

        if (plan.get().getUserId() != userId) {
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
        if (plan.isEmpty()) {
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
        if (p.isEmpty()) {
            return ServiceResult.failure("Plan not found");
        }

        try {
            planRepository.save(plan);
            return ServiceResult.success(null);
        } catch (Exception e) {
            return ServiceResult.failure(e.getMessage());
        }
    }

    private void updatePlanStatus(Plan plan) {
        var s = plan.getStartTime();
        var e = plan.getEndTime();
        var now = java.time.LocalDateTime.now();

        var sd = s.toLocalDate();
        var ed = e.toLocalDate();
        var nd = now.toLocalDate();

        var user = userRepository.findById(plan.getUserId()).get();

        var er = electricityRecordRepository.findByRecordDateBetweenAndUser(sd, nd.isBefore(ed) ? nd : ed, user);
        var sum = er.get().stream().mapToDouble(x -> x.getElectricityConsumed().doubleValue()).sum();

        plan.setCurrentEc(sum);

        if (sum >= plan.getTargetEc()) {
            plan.setStatus(Plan.Status.FAILED);
        } else {
            if (nd.isBefore(ed)) {
                plan.setStatus(Plan.Status.IN_PROGRESS);
            } else {
                plan.setStatus(Plan.Status.COMPLETED);
            }
        }

    }
}
