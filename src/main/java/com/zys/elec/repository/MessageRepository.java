package com.zys.elec.repository;

import com.zys.elec.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Find messages if senderId or receiverId is equal to userId
    List<Message> findBySenderIdOrReceiverId(Long userId1, Long userId2);

    // Find messages between two users
    default List<Message> findMessagesBetweenUsers(Long userId1, Long userId2) {
        List<Message> messages = findBySenderIdAndReceiverId(userId1, userId2);
        messages.addAll(findByReceiverIdAndSenderId(userId1, userId2));
        return messages;
    }

    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Message> findByReceiverIdAndSenderId(Long receiverId, Long senderId);

    List<Message> findByReceiverIdAndIsRead(Long receiverId, boolean isRead);

    List<Message> findByReceiverIdAndSenderIdAndIsRead(Long receiverId, Long senderId, boolean isRead);

    // Pagination methods
    Page<Message> findBySenderId(Long senderId, Pageable pageable);

    Page<Message> findByReceiverId(Long receiverId, Pageable pageable);

    Page<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId, Pageable pageable);

    Page<Message> findByReceiverIdAndSenderId(Long receiverId, Long senderId, Pageable pageable);

    // Count methods

    int countBySenderIdAndReceiverId(Long senderId, Long receiverId);

    int countByReceiverIdAndSenderId(Long receiverId, Long senderId);

    int countByReceiverIdAndSenderIdAndIsRead(Long receiverId, Long senderId, boolean isRead);

}