package com.zys.elec.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.ElectricityRecord;

@Service
public interface ElectricityRecordService {
    ServiceResult<ElectricityRecord> save(ElectricityRecord electricityRecord);

    ServiceResult<ElectricityRecord> getById(Long id);

    ServiceResult<ElectricityRecord> delete(Long id);

    ServiceResult<ElectricityRecord> update(ElectricityRecord electricityRecord);

    ServiceResult<List<ElectricityRecord>> getAll();

    ServiceResult<List<ElectricityRecord>> getByUserId(Long userId);

    ServiceResult<List<ElectricityRecord>> getByDate(LocalDate date);

    ServiceResult<List<ElectricityRecord>> getByDateRange(LocalDate startDate, LocalDate endDate);

    ServiceResult<List<ElectricityRecord>> getByDateRangeAndUserId(LocalDate startDate, LocalDate endDate, Long userId);

}