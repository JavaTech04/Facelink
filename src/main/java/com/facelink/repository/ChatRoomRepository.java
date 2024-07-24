package com.facelink.repository;

import com.facelink.entity.ChatRoom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("FROM ChatRoom WHERE senderId = :senderId and recipientId = :recipientId")
    Optional<ChatRoom> findBySenderIdAndRecipientId(Long senderId, Long recipientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatRoom WHERE senderId = :id OR recipientId = :id")
    void deleteAccountMain(Long id);
}
