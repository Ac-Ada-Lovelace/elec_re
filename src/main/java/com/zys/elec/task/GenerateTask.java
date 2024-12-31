// package com.zys.elec.task;

// import com.zys.elec.entity.ElectricityRecord;
// import com.zys.elec.entity.Predict;
// import com.zys.elec.service.ElectricityRecordService;
// import com.zys.elec.service.PredictService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.ApplicationArguments;
// import org.springframework.boot.ApplicationRunner;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;

// @Component
// public class GenerateTask implements ApplicationRunner {

//     @Autowired
//     private ElectricityRecordService electricityRecordService;

//     @Autowired
//     private PredictService predictService;

//     @Override
//     public void run(ApplicationArguments args) throws Exception {
//         generatePredicts();
//     }

//     @Scheduled(cron = "0 0 * * * ?") // 每小时执行一次
//     public void generatePredicts() {
//         var res = electricityRecordService.getRecordsWithoutPredicts();
//         if (!res.isSuccess()) {
//             return;
//         }
//         var recordsWithoutPredicts = res.getData();
//         for (ElectricityRecord record : recordsWithoutPredicts) {
//             Predict predict = new Predict();
//             predict.setStrategy("LSTM"); // 假设使用LSTM策略
//             predict.setPredictedAt(LocalDateTime.now());
//             predict.setTargetDate(record.getRecordDate().plusDays(1)); // 假设预测目标日期为记录日期的下一天
//             predict.setUser(record.getUser());
//             predict.setElectricityRecord(record);

//             // 生成预测值的逻辑（这里假设为一个随机值）
//             predict.setPredictedValue(generatePredictedValue(record));

//             predictService.save(predict);
//         }
//     }

//     private BigDecimal generatePredictedValue(ElectricityRecord record) {
//         // 0.9 - 1.1 之间的随机数
//         var randomValue = new BigDecimal(Math.random() * 0.2 + 0.9);
//         return randomValue.multiply(record.getElectricityConsumed());
//     }
// }