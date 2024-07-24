package com.facelink.repository;

import com.facelink.entity.ChatMessage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("FROM ChatMessage WHERE chatName = :chatName")
    List<ChatMessage> findByChatName(String chatName);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatMessage WHERE senderId = :id OR recipientId = :id")
    void deleteAccountMain(Long id);
}
