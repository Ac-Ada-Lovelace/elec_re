package com.zys.elec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.DataDTO;
import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.entity.Predict;
import com.zys.elec.entity.User;
import com.zys.elec.repository.ElectricityRecordRepository;
import com.zys.elec.repository.PredictRepository;
import com.zys.elec.repository.UserRepository;
import com.zys.elec.service.DataService;

import java.util.Collections;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElectricityRecordRepository electricityRecordRepository;

    @Autowired
    private PredictRepository predictRepository;

    @Override
    public ServiceResult<DataDTO> getData(DataDTO dataDTO) {
        User user = userRepository.findById(Long.parseLong(dataDTO.getUserId())).orElse(null);
        if (user == null) {
            return ServiceResult.failure("User not found");
        }

        List<ElectricityRecord> records = electricityRecordRepository.findByRecordDateBetweenAndUser(
                dataDTO.getStartDate(), dataDTO.getEndDate(), user).orElse(Collections.emptyList());

        for (ElectricityRecord record : records) {
            Predict predict = null;
            if (dataDTO.isWithPredict()) {
                var res = predictRepository.findByUserAndTargetDate(user, record.getRecordDate());
                if (res.isPresent()) {
                    predict = res.get().get(0);
                }
                
            }
            dataDTO.addData(record, predict);
        }

        return ServiceResult.success(dataDTO);
    }
}
