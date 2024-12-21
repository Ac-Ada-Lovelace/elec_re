package com.zys.elec.dto;

import com.zys.elec.entity.ElectricityRecord;

import lombok.Data;

@Data
public class ElectricityRecordDTO {
    ElectricityRecord electricityRecord;
    UserDTO userDTO;

    public static ElectricityRecordDTO fromEntity(ElectricityRecord electricityRecord) {
        var electricityRecordDTO = new ElectricityRecordDTO();
        electricityRecordDTO.setUserDTO(UserDTO.fromEntity(electricityRecord.getUser()));
        electricityRecord.setUser(null);
        electricityRecordDTO.setElectricityRecord(electricityRecord);
        return electricityRecordDTO;

    }

}
