package com.facelink.repository;

import com.facelink.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("FROM ChatMessage WHERE chatName = :chatName")
    List<ChatMessage> findByChatName(String chatName);
}
