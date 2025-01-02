package com.zys.elec.service.impl;

import com.zys.elec.common.ServiceResult;
import com.zys.elec.dto.ConversationDTO;
import com.zys.elec.dto.MessageDTO;
import com.zys.elec.entity.Message;
import com.zys.elec.repository.MessageRepository;
import com.zys.elec.service.MessageService;
import com.zys.elec.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    private boolean IsUserExists(Long userId) {
        return userService.getUserById(userId).isSuccess();
    }

    @Override
    public ServiceResult<List<Message>> getConversation(Long senderId, Long receiverId) {

        if (!IsUserExists(senderId) || !IsUserExists(receiverId)) {
            return new ServiceResult<>(false, "Sender or receiver not found", null);
        }

        var res = messageRepository.findMessagesBetweenUsers(senderId, receiverId);
        if (res.isEmpty()) {
            return new ServiceResult<>(false, "No messages found", null);
        }
        return new ServiceResult<>(true, "Messages found", res);

    }

    @Override
    public ServiceResult<Long> sendMessage(Message message) {
        // Validate message timestamp, not null, not in the future, not more than 3
        // minutes in the past
        message.setSentAt(message.getSentAt() == null ? LocalDateTime.now() : message.getSentAt());
        final LocalDateTime sentAt = message.getSentAt();

        if (sentAt == null) {
            return new ServiceResult<>(false, "SentAt is required", 0L);
        }
        final LocalDateTime now = LocalDateTime.now();
        if (sentAt.isAfter(now)) {
            return new ServiceResult<>(false, "SentAt cannot be in the future", 0L);
        }
        if (sentAt.isBefore(now.minusMinutes(3))) {
            return new ServiceResult<>(false, "SentAt cannot be more than 3 minutes in the past", 0L);
        }

        // Validate sender and receiver
        if (!IsUserExists(message.getSenderId()) || !IsUserExists(message.getReceiverId())) {
            return new ServiceResult<>(false, "Sender or receiver not found", 0L);
        }

        Message savedMessage = null;
        try {
            savedMessage = messageRepository.save(message);
        } catch (Exception e) {
            return ServiceResult.failure("Failed to send message: " + e.getMessage());
            // return new ServiceResult<>(false, "Failed to send message: " + e.getMessage(), 0L);
        }
        return ServiceResult.success(savedMessage.getId());
        // return new ServiceResult<>(true, "Message sent", savedMessage.getId());
    }

    @Override
    public ServiceResult<Integer> getUnreadMessagesCountToUser(Long userId) {
        if (userService.getUserById(userId) == null) {
            return new ServiceResult<>(false, "User not found", 0);
        }

        var res = messageRepository.findByReceiverIdAndIsRead(userId, false);
        if (res.isEmpty()) {
            return new ServiceResult<>(false, "No unread messages found", 0);
        }
        return new ServiceResult<>(true, "Unread messages found", res.size());
    }

    @Override
    public ServiceResult<Integer> getUnreadMessagesCountToUserFromSpecUser(Long userId, Long senderId) {
        var res = messageRepository.findBySenderIdAndReceiverId(senderId, userId);
        if (res.isEmpty()) {
            return new ServiceResult<>(false, "No unread messages found", 0);
        }
        return new ServiceResult<>(true, "Unread messages found", res.size());
    }

    @Override
    public ServiceResult<List<Message>> getUnreadMessagesToUser(Long userId) {
        if (userService.getUserById(userId) == null) {
            return new ServiceResult<>(false, "User not found", null);
        }

        var res = messageRepository.findByReceiverIdAndIsRead(userId, false);
        if (res.isEmpty()) {
            return new ServiceResult<>(false, "No unread messages found", null);
        }
        return new ServiceResult<>(true, "Unread messages found", res);
    }

    @Override
    public ServiceResult<List<Message>> getUnreadMessageToUserFromSpecUser(Long userId, Long senderId) {
        var res = messageRepository.findBySenderIdAndReceiverId(senderId, userId);
        if (res.isEmpty()) {
            return new ServiceResult<>(false, "No unread messages found", null);
        }
        return new ServiceResult<>(true, "Unread messages found", res);
    }

    @Override
    public ServiceResult<Boolean> deleteMessageIfSentWithin3Minutes(Long messageId) {
        var message = messageRepository.findById(messageId).orElse(null);
        if (message == null) {
            return new ServiceResult<>(false, "Message not found", false);
        }

        // Convert LocalDateTime to milliseconds
        LocalDateTime sentAt = message.getSentAt();
        long sentAtMillis = Date.from(sentAt.atZone(ZoneId.systemDefault()).toInstant()).getTime();

        // Get current time in milliseconds
        long currentMillis = System.currentTimeMillis();

        // Calculate the difference in minutes
        long minutesBetween = (currentMillis - sentAtMillis) / (1000 * 60);

        if (minutesBetween <= 3) {
            messageRepository.delete(message);
            return new ServiceResult<>(true, "Message deleted", true);
        } else {
            return new ServiceResult<>(false, "Cannot delete message after 3 minutes", false);
        }
    }

    @Override
    public ServiceResult<ConversationDTO> getConversationDTO(Long senderId, Long receiverId) {
        var messages = getConversation(senderId, receiverId).getData();

        if (messages == null) {
            return new ServiceResult<>(false, "No messages found", null);
        }
        var messagesDTO = messages.stream().map(MessageDTO::fromEntity).toList();
        var conversationDTO = new ConversationDTO();
        conversationDTO.setMessages(messagesDTO);
        return new ServiceResult<>(true, "Conversation found", conversationDTO);
    }

}
