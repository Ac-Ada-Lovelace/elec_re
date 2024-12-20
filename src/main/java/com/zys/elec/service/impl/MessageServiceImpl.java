package com.zys.elec.service.impl;

import com.zys.elec.common.ServiceResult;
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

    @Override
    public ServiceResult<List<Message>> getConversation(Long senderId, Long receiverId) {
        var res = messageRepository.findMessagesBetweenUsers(senderId, receiverId);
        if (res.isEmpty()) {
            return new ServiceResult<>(false, "No messages found", null);
        }
        return new ServiceResult<>(true, "Messages found", res);

    }

    @Override
    public ServiceResult<Boolean> sendMessage(Message message) {
        var rec = message.getReceiverId();
        var sen = message.getSenderId();

        var receiver = userService.getUserById(rec);
        var sender = userService.getUserById(sen);

        if (receiver == null || sender == null) {
            return new ServiceResult<>(false, "Receiver or sender not found", false);
        }

        try {
            messageRepository.save(message);
            return new ServiceResult<>(true, "Message sent", true);
        } catch (Exception e) {
            return new ServiceResult<>(false, "Failed to send message: " + e.getMessage(), false);
        }

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

}
