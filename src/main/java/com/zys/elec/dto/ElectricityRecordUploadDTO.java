package com.zys.elec.dto;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class ElectricityRecordUploadDTO {
    private BigDecimal electricityConsumed;
    private Long userId;

}