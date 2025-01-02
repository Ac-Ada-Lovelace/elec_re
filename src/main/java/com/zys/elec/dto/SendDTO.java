package com.zys.elec.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.zys.elec.entity.Message;

import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
public class SendDTO{
    private Long senderId;
    private Long receiverId;

    @NonNull
    private String content;



}

