package com.facelink.repository;

import com.facelink.entity.Account;
import com.facelink.entity.ListFriend;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ListFriendsRepository extends JpaRepository<ListFriend, Integer> {
    @Query("FROM ListFriend WHERE accountId = :id and status = 1")
    Set<?> getFriendByUser(@Param("id") Long id);

    @Query("SELECT u FROM ListFriend u WHERE (u.accountId = :id and u.friendInfo.id = :userId) OR (u.accountId = :userId and u.friendInfo.id = :id)")
    List<ListFriend> hasFriend(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT a FROM ListFriend l INNER JOIN Account a ON a.id = l.accountId WHERE l.friendInfo.id = :userId and l.status = 0")
    List<Account> friendRequests(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ListFriend WHERE (accountId = :id and friendInfo.id = :userId) OR (accountId = :userId AND friendInfo.id = :id)")
    void removeFriend(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE ListFriend SET status = 1 WHERE accountId = :id")
    void updateStatusFriend(@Param("id") Long id);
}
