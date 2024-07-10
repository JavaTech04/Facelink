package com.facelink.repository;

import com.facelink.entity.ListFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ListFriendsRepository extends JpaRepository<ListFriend, Integer> {
    @Query("FROM ListFriend WHERE accountId = :id")
    Set<ListFriend> getFriendByUser(@Param("id") Long id);
}
