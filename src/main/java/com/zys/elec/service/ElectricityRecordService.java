package com.zys.elec.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.ElectricityRecordDTO;
import com.zys.elec.dto.ElectricityRecordUploadDTO;
import com.zys.elec.entity.ElectricityRecord;

@Service
public interface ElectricityRecordService {

    ServiceResult<ElectricityRecord> uploadRecord(ElectricityRecordUploadDTO record);

    ServiceResult<ElectricityRecord> addNew(ElectricityRecord electricityRecord);

    ServiceResult<ElectricityRecord> getById(Long id);

    ServiceResult<ElectricityRecord> delete(Long id);

    ServiceResult<ElectricityRecord> update(ElectricityRecord electricityRecord);

    ServiceResult<List<ElectricityRecord>> getAll();

    ServiceResult<List<ElectricityRecord>> getByUserId(Long userId);

    ServiceResult<List<ElectricityRecord>> getByDate(LocalDate date);

    ServiceResult<List<ElectricityRecord>> getByDateRange(LocalDate startDate, LocalDate endDate);

    ServiceResult<List<ElectricityRecord>> getByDateRangeAndUserId(LocalDate startDate, LocalDate endDate, Long userId);

    ServiceResult<List<ElectricityRecord>> getRecordsWithoutPredicts();


    ServiceResult<Map<String, ElectricityRecordDTO>> getConsumptionByDayOfWeek(Long userId, LocalDate startDate, LocalDate endDate);

    ServiceResult<Map<String, ElectricityRecordDTO>> getConsumptionByDayOfWeek(Long userId);
}