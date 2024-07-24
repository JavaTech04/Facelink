package com.facelink.repository;

import com.facelink.entity.Reaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.account.id = :idAccount and r.post.id = :idPost")
    int hasLike(@Param("idAccount") Long idAccount, @Param("idPost") Long idPost);
    @Transactional
    @Modifying
    @Query("DELETE FROM Reaction r WHERE r.account.id = :idAccount and r.post.id = :idPost")
    void unlike(@Param("idAccount") Long idAccount, @Param("idPost") Long idPost);
    @Query("SELECT r.type FROM Reaction r WHERE r.account.id = :idAccount and r.post.id = :idPost")
    String getReactionType(@Param("idAccount") Long idAccount, @Param("idPost") Long idPost);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reaction WHERE post.id = :idPost")
    void deleteReactionByPost(@Param("idPost") Long idPost);

    @Modifying @Transactional @Query("DELETE FROM Reaction WHERE account.id = :id")
    void deleteAccountMain(Long id);
}
