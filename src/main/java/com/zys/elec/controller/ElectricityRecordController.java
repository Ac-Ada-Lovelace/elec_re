package com.zys.elec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.service.ElectricityRecordService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/api/electricity-record")
public class ElectricityRecordController {
    @Autowired
    private ElectricityRecordService electricityRecordService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseResult<ElectricityRecord> getElectricityRecordById(@RequestParam Long id) {
        var result = electricityRecordService.getById(id);
        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }

    }

    @GetMapping("/user/{userId}/date-range")
    @ResponseBody
    public ResponseResult<List<ElectricityRecord>> getElectricityRecordByUserIdAndDateRange(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        var result = electricityRecordService.getByDateRangeAndUserId(startDate, endDate, userId);
        if (result.isSuccess()) {
            return ResponseResult.success(result.getData());
        } else {
            return ResponseResult.failure(result.getMessage());
        }
    }

}
