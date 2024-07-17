package com.facelink.repository;

import com.facelink.entity.Post;
import com.facelink.enums.PostAudience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("FROM Post WHERE account.id = :id ORDER BY createDate DESC ")
    List<?> getPostByAccount(@Param("id") Long id);

    @Query("FROM Post WHERE postAudience = :postAudience ORDER BY RAND()")
    List<?> getPostsPublic(@Param("postAudience") PostAudience postAudience);
}
