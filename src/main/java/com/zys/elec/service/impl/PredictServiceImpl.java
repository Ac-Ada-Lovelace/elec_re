package com.zys.elec.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Predict;
import com.zys.elec.repository.PredictRepository;
import com.zys.elec.service.ElectricityRecordService;
import com.zys.elec.service.PredictService;
import com.zys.elec.service.UserService;

@Service
public class PredictServiceImpl implements PredictService {


    @Autowired
    private ElectricityRecordService electricityRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private PredictRepository predictRepository;

    @Override
    public ServiceResult<Predict> save(Predict predict) {
        try {
            Predict savedPredict = predictRepository.save(predict);
            return ServiceResult.success(savedPredict);
        } catch (Exception e) {
            return ServiceResult.failure("Failed to save predict: " + e.getMessage());
        }
    }

    @Override
    public ServiceResult<Predict> getById(Long id) {
        var predictOpt = predictRepository.findById(id);
        if (predictOpt.isEmpty()) {
            return ServiceResult.failure("Predict not found");
        }
        return ServiceResult.success(predictOpt.get());
    }

    @Override
    public ServiceResult<Void> deleteById(Long id) {
        var predictOpt = predictRepository.findById(id);
        if (predictOpt.isEmpty()) {
            return ServiceResult.failure("Predict not found");
        }
        predictRepository.deleteById(id);
        return ServiceResult.success(null);
    }

    @Override
    public ServiceResult<List<Predict>> getAll() {
        List<Predict> predicts = predictRepository.findAll();
        return ServiceResult.success(predicts);
    }

    @Override
    public ServiceResult<List<Predict>> getByUserId(Long userId) {

        var user = userService.getUserById(userId);

        if (!user.isSuccess()) {
            return ServiceResult.failure("User not found");
        }

        var res = predictRepository.findByUserId(user.getData().getId());

        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        } else {
            return ServiceResult.failure("Predict not found");
        }
    }

    @Override
    public ServiceResult<List<Predict>> getByDate(LocalDate date) {
        var res = predictRepository.findByTargetDate(date);

        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        } else {
            return ServiceResult.failure("Predict not found");
        }
    }

    @Override
    public ServiceResult<List<Predict>> getByDateRange(LocalDate startDate, LocalDate endDate) {
        var res = predictRepository.findByTargetDateBetween(startDate, endDate);

        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        } else {
            return ServiceResult.failure("Predict not found");
        }
    }

    @Override
    public ServiceResult<List<Predict>> getByDateRangeAndUserId(LocalDate startDate, LocalDate endDate, Long userId) {
        var user = userService.getUserById(userId);

        if (!user.isSuccess()) {
            return ServiceResult.failure("User not found");
        }

        var res = predictRepository.findByTargetDateBetweenAndUserId(startDate, endDate, user.getData().getId());

        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        } else {
            return ServiceResult.failure("Predict not found");
        }
    }

}
