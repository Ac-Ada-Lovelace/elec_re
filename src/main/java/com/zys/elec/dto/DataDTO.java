package com.zys.elec.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DataDTO {

    @Data
    public class PredictDataUnit {
        private String strategy;
        private LocalDate date;
        private double predictvalue;
    }

    @Data
    public class DataUnit {
        private LocalDate date;
        private double realvalue;
        private boolean hasPredict;

    }

    @Data
    public class ResultUnit {
        private LocalDate date;
        private DataUnit real;
        private PredictDataUnit predict;
    }

    private String username;
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean withPredict;
    private List<ResultUnit> result;

    
}
