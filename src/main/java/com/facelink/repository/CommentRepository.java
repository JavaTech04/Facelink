package com.facelink.repository;

import com.facelink.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    @Modifying
    @Query("DELETE Comment WHERE post.id = :id")
    void deleteCommentByPost(@Param("id") Long id);

    @Modifying @Transactional @Query("DELETE FROM Comment WHERE account.id = :id")
    void deleteAccountMain(Long id);
}
