package com.zys.elec.service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.DataDTO;

public interface DataService {
    public ServiceResult<DataDTO> getData(DataDTO dataDTO);
}
