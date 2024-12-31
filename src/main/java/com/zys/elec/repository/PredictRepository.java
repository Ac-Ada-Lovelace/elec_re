package com.zys.elec.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.entity.Predict;

public interface PredictRepository extends JpaRepository<Predict, Long> {

    
    // method to find all predicts by user id
    Optional<List<Predict>> findByUserId(Long userId);

    // method to find all predicts by date
    Optional<List<Predict>> findByTargetDate(LocalDate date);

    // method to find all predicts by date range
    Optional<List<Predict>> findByTargetDateBetween(LocalDate startDate, LocalDate endDate);

    // method to find all predicts by date range and user id
    Optional<List<Predict>> findByTargetDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, Long userId);



}
