package com.zys.elec.service;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.entity.Message;
import java.util.List;

import org.springframework.data.domain.Page;

public interface MessageService {

    ServiceResult<List<Message>> getConversation(Long senderId, Long receiverId);

    ServiceResult<Boolean> sendMessage(Message message);

    ServiceResult<List<Message>> getUnreadMessagesToUser(Long userId);

    ServiceResult<List<Message>> getUnreadMessageToUserFromSpecUser(Long userId, Long senderId);

    ServiceResult<Integer> getUnreadMessagesCountToUser(Long userId);

    ServiceResult<Integer> getUnreadMessagesCountToUserFromSpecUser(Long userId, Long senderId);

    ServiceResult<Boolean> deleteMessageIfSentWithin3Minutes(Long messageId);
}
