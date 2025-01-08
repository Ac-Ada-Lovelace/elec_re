package com.zys.elec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.SignUpDTO;
import com.zys.elec.entity.ElectricityRecord;
import com.zys.elec.entity.Predict;
import com.zys.elec.entity.User;
import com.zys.elec.repository.ElectricityRecordRepository;
import com.zys.elec.repository.PlanRepository;
import com.zys.elec.repository.PostRepository;
import com.zys.elec.repository.PredictRepository;
import com.zys.elec.repository.UserRepository;
import com.zys.elec.service.SignUpService;
import com.zys.elec.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pd")
@RestController
public class PDController {

    @Autowired
    private UserService userService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ElectricityRecordRepository electricityRecordRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PredictRepository predictRepository;

    @Autowired
    private PlanRepository planRepository;

    @GetMapping("/user")
    public ResponseResult<Void> genUser() {
        var signUp = new SignUpDTO();
        signUp.setUsername("view");
        signUp.setPassword("view");
        var user = signUp.ToUser();

        var res = signUpService.signUp(user);
        if (res.isSuccess()) {
            return ResponseResult.success(null);
        } else {
            return ResponseResult.failure(res.getMessage());
        }

    }

    @GetMapping("/gendata")
    public ResponseResult<Void> genData() {

        var user = userRepository.findByUsername("view").get();

        // generate electricity records from 2020-01-01 to yesterday
        var start = LocalDate.of(2020, 1, 1);
        var end = LocalDate.now().minusDays(1);

        var random = new Random();

        for (var date = start; date.isBefore(end); date = date.plusDays(1)) {
            var record = new ElectricityRecord();
            record.setUser(user);
            record.setCreatedAt(date.atStartOfDay());
            record.setUpdatedAt(date.atStartOfDay());
            record.setRecordDate(date);
            // generate a value between 3 and 10

            var value = random.nextDouble() * 7 + 3;
            var bigDecimalValue = BigDecimal.valueOf(value);
            record.setElectricityConsumed(bigDecimalValue);

           record = electricityRecordRepository.save(record);

            var predict = new Predict();
            predict.setElectricityRecord(record);
            predict.setPredictedAt(date.minusDays(1).atStartOfDay());
            predict.setTargetDate(record.getRecordDate());
            predict.setUser(user);
            var lstmRate = random.nextDouble() * 0.1 + 0.95;
            var gnuRate = random.nextDouble() * 0.2 + 0.90;


            predict.setStrategy("LSTM");
            predict.setPredictedValue(bigDecimalValue.multiply(BigDecimal.valueOf(lstmRate)));

            predictRepository.save(predict);
            
            predict.setStrategy("GNU");
            predict.setPredictedValue(bigDecimalValue.multiply(BigDecimal.valueOf(gnuRate)));

            predictRepository.save(predict);

        }

        return ResponseResult.success(null);

    }

}
