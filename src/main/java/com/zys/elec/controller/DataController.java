package com.zys.elec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.DataDTO;
import com.zys.elec.service.DataService;

@RestController
@RequestMapping("/data")
public class DataController {
    
    @Autowired
    private DataService dataService;

    @PostMapping("/get")
    public ResponseResult<DataDTO> getData(@RequestBody DataDTO dataDTO) {

        var result = dataService.getData(dataDTO);

        if (!result.isSuccess()) {
            return ResponseResult.failure(result.getMessage());
        }
        return ResponseResult.success(result.getData());
    
    }
}
