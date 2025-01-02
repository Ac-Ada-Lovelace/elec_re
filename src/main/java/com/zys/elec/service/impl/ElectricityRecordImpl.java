package com.zys.elec.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.ElectricityRecordDTO;
import com.zys.elec.dto.ElectricityRecordUploadDTO;
import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.entity.User;
import com.zys.elec.repository.ElectricityRecordRepository;
import com.zys.elec.repository.PredictRepository;
import com.zys.elec.service.ElectricityRecordService;
import com.zys.elec.service.UserService;

@Service
public class ElectricityRecordImpl implements ElectricityRecordService {
    @Autowired
    private ElectricityRecordRepository electricityRecordRepository;

    @Autowired
    private PredictRepository predictRepository;


    @Autowired
    private UserService userService;

    @Override
    public ServiceResult<ElectricityRecord> addNew(ElectricityRecord electricityRecord) {

        if (electricityRecord.getUser() == null) {
            return ServiceResult.failure("User is required");
        }

        if (electricityRecord.getRecordDate() == null) {
            return ServiceResult.failure("Record date is required");
        }
        var exists = electricityRecordRepository.findByUserAndRecordDate(electricityRecord.getUser(),
                electricityRecord.getRecordDate());
        if (exists.isPresent()) {
            return ServiceResult.failure("Electricity record already exists");
        }
        try {
            var newElectricityRecord = electricityRecordRepository.save(electricityRecord);
            return ServiceResult.success(newElectricityRecord);
        } catch (Exception e) {
            return ServiceResult.failure("Failed to create electricity record");
        }

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
        try {
            var exists = electricityRecordRepository.findById(electricityRecord.getId());
            if (exists.isPresent()) {
                var updatedElectricityRecord = electricityRecordRepository.save(electricityRecord);
                return ServiceResult.success(updatedElectricityRecord);
            } else {
                return ServiceResult.failure("Electricity record not found");
            }
        } catch (Exception e) {
            return ServiceResult.failure("Failed to update electricity record");
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

       var electricityRecords = electricityRecordRepository.findByUser(user);
        if (electricityRecords.isPresent()) {
            return ServiceResult.success(electricityRecords.get());
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
        var user = userService.getUserById(userId).getData().toEntity();
        if (user == null) {
            return ServiceResult.failure("User not found");
        }
        Optional<List<ElectricityRecord>> res = electricityRecordRepository.findByRecordDateBetweenAndUser(startDate,
                endDate,
                user);
        if (res.isPresent()) {
            return ServiceResult.success(res.get());
        }
        return ServiceResult.failure("Electricity record not found");
    }

    @Override
    public ServiceResult<ElectricityRecord> uploadRecord(ElectricityRecordUploadDTO record) {
        var user_id = record.getUserId();
        var user = userService.getUserById(user_id).getData().toEntity();
        if (user == null) {
            return ServiceResult.failure("User not found");
        }
        var exists = electricityRecordRepository.findByUserAndRecordDate(user, LocalDate.now());
        if (exists.isPresent()) {
            return ServiceResult.failure("Electricity record already exists");
        }

        var electricityRecord = new ElectricityRecord();
        electricityRecord.setElectricityConsumed(record.getElectricityConsumed());
        electricityRecord.setRecordDate(LocalDate.now());
        electricityRecord.setUser(user);
        return addNew(electricityRecord);

    }

    @Override
    public ServiceResult<List<ElectricityRecord>> getRecordsWithoutPredicts() {
        var electricityRecords = electricityRecordRepository.findRecordsWithoutPredicts();

        if (electricityRecords.isPresent()) {
            return ServiceResult.success(electricityRecords.get());
        }

        return ServiceResult.failure("Electricity record not found");
    }

    @Override
    public ServiceResult<Map<String, ElectricityRecordDTO>> getConsumptionByDayOfWeek(Long userId, LocalDate startDate,
            LocalDate endDate) {
        var user = userService.getUserById(userId).getData().toEntity();
        if (user == null) {
            return ServiceResult.failure("User not found");
        }

        var records = electricityRecordRepository.findByRecordDateBetweenAndUser(startDate, endDate, user);
        if (records.isEmpty()) {
            return ServiceResult.failure("No records found");
        }

        Map<String, ElectricityRecordDTO> consumptionByDayOfWeek = new HashMap<>();
        // pre add all days of week
        for (int i = 1; i <= 7; i++) {
            var dayOfWeek = String.valueOf(i);
            consumptionByDayOfWeek.put(dayOfWeek, new ElectricityRecordDTO());
        }
        
        for (ElectricityRecord record : records.get()) {
            var dayOfWeek = String.valueOf(record.getRecordDate().getDayOfWeek().getValue());
            var dto = consumptionByDayOfWeek.get(dayOfWeek);
            if (dto.getElectricityRecord() == null) {
                dto.setElectricityRecord(new ElectricityRecord());
                dto.getElectricityRecord().setElectricityConsumed(BigDecimal.ZERO);
            }
            dto.getElectricityRecord().setElectricityConsumed(
                    dto.getElectricityRecord().getElectricityConsumed().add(record.getElectricityConsumed()));
        }

        return ServiceResult.success(consumptionByDayOfWeek);
    }

    @Override
    public ServiceResult<Map<String, ElectricityRecordDTO>> getConsumptionByDayOfWeek(Long userId) {
        var user = userService.getUserById(userId).getData().toEntity();
        if (user == null) {
            return ServiceResult.failure("User not found");
        }

        var records = electricityRecordRepository.findByUser(user);
        if (records.isEmpty()) {
            return ServiceResult.failure("No records found");
        }

        Map<String, ElectricityRecordDTO> consumptionByDayOfWeek = new HashMap<>();
        // pre add all days of week
        for (int i = 1; i <= 7; i++) {
            var dayOfWeek = String.valueOf(i);
            consumptionByDayOfWeek.put(dayOfWeek, new ElectricityRecordDTO());
        }

        for (ElectricityRecord record : records.get()) {
            var dayOfWeek = String.valueOf(record.getRecordDate().getDayOfWeek().getValue());
            var dto = consumptionByDayOfWeek.get(dayOfWeek);
            if (dto.getElectricityRecord() == null) {
                dto.setElectricityRecord(new ElectricityRecord());
                dto.getElectricityRecord().setElectricityConsumed(BigDecimal.ZERO);
            }
            dto.getElectricityRecord().setElectricityConsumed(
                    dto.getElectricityRecord().getElectricityConsumed().add(record.getElectricityConsumed()));
        }

        return ServiceResult.success(consumptionByDayOfWeek);
    }



}
