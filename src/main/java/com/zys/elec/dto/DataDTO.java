package com.zys.elec.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.entity.Predict;

import lombok.Data;

@Data
public class DataDTO {

    @Data
    public class PredictDataUnit {
        private String strategy;
        private LocalDate date;
        private BigDecimal predictvalue;
    }

    @Data
    public class DataUnit {
        private LocalDate date;
        private BigDecimal realvalue;
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

    public void addData(ElectricityRecord record, Predict predict) {
        DataUnit dataUnit = new DataUnit();
        PredictDataUnit predictDataUnit = new PredictDataUnit();
        if (record != null) {
            dataUnit.setDate(record.getRecordDate());
            dataUnit.setRealvalue(record.getElectricityConsumed());
            dataUnit.setHasPredict(predict != null);
        }
        if (predict != null) {
            predictDataUnit.setDate(predict.getTargetDate());
            predictDataUnit.setPredictvalue(predict.getPredictedValue());
            predictDataUnit.setStrategy(predict.getStrategy());
        }

        ResultUnit resultUnit = new ResultUnit();
        resultUnit.setDate(dataUnit.getDate());
        resultUnit.setReal(dataUnit);
        resultUnit.setPredict(predictDataUnit);

        result.add(resultUnit);
    }
}
