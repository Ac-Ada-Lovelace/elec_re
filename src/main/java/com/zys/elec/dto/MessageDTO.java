package com.zys.elec.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.zys.elec.entity.Message;

import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
public class MessageDTO {
    private Long senderId;
    private Long receiverId;

    private LocalDateTime sentAt;

    @NonNull
    private String content;

    public static MessageDTO fromEntity(Message message) {
        var messageDTO = new MessageDTO();
        messageDTO.setSenderId(message.getSenderId());
        messageDTO.setReceiverId(message.getReceiverId());
        messageDTO.setContent(message.getContent());
        messageDTO.setSentAt(message.getSentAt());

        return messageDTO;
    }

    public Message toEntity() {
        var message = new Message();
        message.setSenderId(this.senderId);
        message.setReceiverId(this.receiverId);
        message.setContent(this.content);
        message.setSentAt(this.sentAt);
        return message;
    }

}
