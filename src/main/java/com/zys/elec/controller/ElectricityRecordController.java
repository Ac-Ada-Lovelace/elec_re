package com.zys.elec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.ElectricityRecordDTO;
import com.zys.elec.dto.ElectricityRecordUploadDTO;
import com.zys.elec.service.ElectricityRecordService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/electricity-record")
public class ElectricityRecordController {
    @Autowired
    private ElectricityRecordService electricityRecordService;

    @PostMapping("/upload")
    public ResponseResult<ElectricityRecordDTO> uploadElectricityRecord(
            @RequestBody ElectricityRecordUploadDTO record) {
        var result = electricityRecordService.uploadRecord(record);

        if (!result.isSuccess()) {
            return ResponseResult.failure(result.getMessage());
        }
        return ResponseResult.success(ElectricityRecordDTO.fromEntity(result.getData()));
    }

    // example: http://localhost:8080/electricity-record/1
    @GetMapping("/querybyid")
    @ResponseBody
    public ResponseResult<ElectricityRecordDTO> getElectricityRecordById(@RequestParam Long id) {
        var result = electricityRecordService.getById(id);

        if (!result.isSuccess()) {
            return ResponseResult.failure(result.getMessage());
        }
        return ResponseResult.success(ElectricityRecordDTO.fromEntity(result.getData()));
    }

    // example:
    // http://localhost:8080/electricity-record/querybyrange?userId=1&startDate=2021-01-01&endDate=2021-12-31
    @GetMapping("/querybyrange")
    @ResponseBody
    public ResponseResult<List<ElectricityRecordDTO>> getElectricityRecordByUserIdAndDateRange(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        var result = electricityRecordService.getByDateRangeAndUserId(startDate, endDate, userId);

        if (!result.isSuccess()) {
            return ResponseResult.failure(result.getMessage());
        }
        var electricityRecordDTOs = result.getData().stream().map(ElectricityRecordDTO::fromEntity).toList();
        return ResponseResult.success(electricityRecordDTOs);

    }

}
