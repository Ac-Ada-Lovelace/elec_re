package com.zys.elec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zys.elec.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    Optional<List<Plan>> findByUserId(Long userId);

    Optional<Plan> findByIdAndUserId(Long planId, Long userId);

    void deleteByIdAndUserId(Long planId, Long userId);


} 