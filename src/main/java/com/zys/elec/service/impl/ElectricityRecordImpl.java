package com.zys.elec.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.entity.User;
import com.zys.elec.repository.ElectricityRecordRepository;
import com.zys.elec.service.ElectricityRecordService;
import com.zys.elec.service.UserService;

@Service
public class ElectricityRecordImpl implements ElectricityRecordService {
    @Autowired
    private ElectricityRecordRepository electricityRecordRepository;

    @Autowired
    private UserService userService;

    @Override
    public ServiceResult<ElectricityRecord> save(ElectricityRecord electricityRecord) {
        var exists = userService.getById(electricityRecord.getId());
        if (!exists.isSuccess()) {
            return ServiceResult.failure("User does not exist");
        }

        var newElectricityRecord = electricityRecordRepository.save(electricityRecord);

        if (newElectricityRecord != null) {
            return ServiceResult.success(newElectricityRecord);
        }
        return ServiceResult.failure("Failed to create electricity record");

    }

    @Override
    public ServiceResult<ElectricityRecord> getById(Long id) {
        var electricityRecord = electricityRecordRepository.findById(id);
        if (electricityRecord.isPresent()) {
            return ServiceResult.success(electricityRecord.get());
        }
        return ServiceResult.failure("Electricity record not found");
    }

    @Override
    public ServiceResult<ElectricityRecord> delete(Long id) {
        var electricityRecord = electricityRecordRepository.findById(id);
        if (electricityRecord.isPresent()) {
            electricityRecordRepository.deleteById(id);
            return ServiceResult.success(null);
        }
        return ServiceResult.failure("Electricity record not found");
    }

    @Override
    public ServiceResult<ElectricityRecord> update(ElectricityRecord electricityRecord) {
        var exists = electricityRecordRepository.findById(electricityRecord.getId());
        if (exists.isPresent()) {
            var updatedElectricityRecord = electricityRecordRepository.save(electricityRecord);
            return ServiceResult.success(updatedElectricityRecord);
        } else {
            return ServiceResult.failure("Electricity record not found");
        }
    }

    @Override
    public ServiceResult<List<ElectricityRecord>> getAll() {
        var electricityRecords = electricityRecordRepository.findAll();
        return ServiceResult.success(electricityRecords);
    }

    @Override
    public ServiceResult<List<ElectricityRecord>> getByUserId(Long userId) {
        User user = new User();
        user.setId(userId);

        Optional<ElectricityRecord> electricityRecords = electricityRecordRepository.findByUser(user);
        if (electricityRecords.isPresent()) {
            return ServiceResult.success(List.of(electricityRecords.get()));
        }
        return ServiceResult.failure("Electricity record not found");
    }

    @Override
    public ServiceResult<List<ElectricityRecord>> getByDate(LocalDate date) {
        Optional<List<ElectricityRecord>> res = electricityRecordRepository.findByRecordDate(date);
        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        }
        return ServiceResult.failure("Electricity record not found");
    }

    @Override
    public ServiceResult<List<ElectricityRecord>> getByDateRange(LocalDate startDate, LocalDate endDate) {
        Optional<List<ElectricityRecord>> res = electricityRecordRepository.findByRecordDateBetween(startDate, endDate);
        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        }
        return ServiceResult.failure("Electricity record not found");
    }

    @Override
    public ServiceResult<List<ElectricityRecord>> getByDateRangeAndUserId(LocalDate startDate, LocalDate endDate,
            Long userId) {
        var user = userService.getById(userId).getData().toEntity();
        if (user == null) {
            return ServiceResult.failure("User not found");
        }
        Optional<List<ElectricityRecord>> res = electricityRecordRepository.findByRecordDateBetweenAndUser(startDate, endDate,
                user);
        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        }
        return ServiceResult.failure("Electricity record not found");
    }

    // CRUD

}
