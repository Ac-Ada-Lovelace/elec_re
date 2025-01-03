package com.zys.elec.dto;



import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
public class SendDTO{
    private Long senderId;
    private Long receiverId;

    @NonNull
    private String content;



}

