package com.zys.elec.controller;

import com.zys.elec.common.ResponseResult;
import com.zys.elec.dto.MessageDTO;
import com.zys.elec.entity.Message;
import com.zys.elec.service.MessageService;

import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin(originPatterns = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/conversation")
    public ResponseResult<List<Message>> getConversation(@NonNull @RequestParam Long senderId,
            @NonNull @RequestParam Long receiverId) {
        var res = messageService.getConversation(senderId, receiverId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());

        }

    }

    @PostMapping("/send")
    public ResponseResult<Long> sendMessage(@NonNull @RequestBody MessageDTO message) {

        if(message.getContent().isEmpty()){
            return ResponseResult.failure("Content is required");
        }

        var messageEntity = message.toEntity();

        // Validate sender and receiver
        var res = messageService.sendMessage(messageEntity);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());

        }

    }

    @GetMapping("/unread")
    public ResponseResult<List<Message>> getUnreadMessagesToUser(@NonNull @RequestParam Long userId) {
        var res = messageService.getUnreadMessagesToUser(userId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());

        }

    }

    @GetMapping("/unread/from")
    public ResponseResult<List<Message>> getUnreadMessageToUserFromSpecUser(@NonNull @RequestParam Long userId,
            @NonNull @RequestParam Long senderId) {
        var res = messageService.getUnreadMessageToUserFromSpecUser(userId, senderId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());

        }

    }

    @GetMapping("/unread/count")
    public ResponseResult<Integer> getUnreadMessagesCountToUser(@NonNull @RequestParam Long userId) {
        var res = messageService.getUnreadMessagesCountToUser(userId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());

        }

    }

    @GetMapping("/unread/count/from")
    public ResponseResult<Integer> getUnreadMessagesCountToUserFromSpecUser(@NonNull @RequestParam Long userId,
            @NonNull @RequestParam Long senderId) {
        var res = messageService.getUnreadMessagesCountToUserFromSpecUser(userId, senderId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());

        }

    }

    @DeleteMapping("/delete")
    public ResponseResult<Boolean> deleteMessageIfSentWithin3Minutes(@NonNull @RequestParam Long messageId) {
        var res = messageService.deleteMessageIfSentWithin3Minutes(messageId);

        if (res.isSuccess()) {
            return ResponseResult.success(res.getData());
        } else {
            return ResponseResult.failure(res.getMessage());

        }

    }
}
