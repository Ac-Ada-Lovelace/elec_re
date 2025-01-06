package com.zys.elec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.entity.Plan;
import com.zys.elec.service.PlanService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping("/getuserplans")
    public ResponseResult<List<Plan>> getMethodName(@RequestParam Long userId) {
        var res = planService.listPlans(userId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseResult<Void> createPlan(@RequestBody Plan plan) {
        var res = planService.createPlan(plan);

        if (res.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(res.getMessage());
        }
    }

    @GetMapping("/update")
    public ResponseResult<Void> updatePlanContent(Plan plan) {
        var res = planService.updatePlanContent(plan);

        if (res.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(res.getMessage());
        }
    }

    @GetMapping("/complete")
    public ResponseResult<Void> completePlan(@RequestParam Long planId) {
        var res = planService.completePlan(planId);

        if (res.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(res.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseResult<Void> cancelPlan(@RequestParam Long planId, @RequestParam Long userId) {
        var res = planService.cancelPlan(planId, userId);

        if (res.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(res.getMessage());
        }
    }

}
