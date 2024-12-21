package com.zys.elec.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.zys.elec.entity.ElectricityRecord;

@Data
public class ElectricityRecordUploadDTO {
    private BigDecimal electricityConsumed;
    private Long userId;

}