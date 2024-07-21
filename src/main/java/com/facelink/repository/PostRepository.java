package com.facelink.repository;

import com.facelink.entity.Post;
import com.facelink.enums.PostAudience;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("FROM Post WHERE account.id = :id AND account.isLocked = false ORDER BY createDate DESC ")
    Page<?> getPostByAccount(@Param("id") Long id, Pageable pageable);

    @Query("FROM Post WHERE postAudience = :postAudience AND account.isLocked = false  ORDER BY RAND()")
    Page<?> getPostsPublic(@Param("postAudience") PostAudience postAudience, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Post SET content = :content WHERE id = :id")
    void updatePost(@Param("id") Long id, @Param("content") String content);

    @Query("FROM Post WHERE type = 'CONTENT_VIDEO_LINK' and postAudience = 'PUBLIC' AND account.isLocked = false ORDER BY RAND()")
    Page<?> getVideoLinksPublic(Pageable pageable);
}
