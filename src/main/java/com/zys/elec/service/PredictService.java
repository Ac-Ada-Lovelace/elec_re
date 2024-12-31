package com.zys.elec.service;

import java.time.LocalDate;
import java.util.List;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Predict;

public interface PredictService {

    ServiceResult<Predict> save(Predict predict);

    ServiceResult<Predict> getById(Long id);

    ServiceResult<Void> deleteById(Long id);

    ServiceResult<List<Predict>> getAll();

    ServiceResult<List<Predict>> getByUserId(Long userId);

    ServiceResult<List<Predict>> getByDate(LocalDate date);

    ServiceResult<List<Predict>> getByDateRange(LocalDate startDate, LocalDate endDate);

    ServiceResult<List<Predict>> getByDateRangeAndUserId(LocalDate startDate, LocalDate endDate, Long userId);

}