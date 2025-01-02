package com.zys.elec.dto;

import java.util.List;

import lombok.Data;

@Data
public class ConversationDTO{
    private UserDTO user;

    private List<MessageDTO> messages;

    
}