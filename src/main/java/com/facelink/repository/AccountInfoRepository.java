package com.facelink.repository;

import com.facelink.entity.AccountInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {
    @Query("FROM AccountInfo WHERE account.id != :id")
    List<?> getAccountInfoByAccountId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE AccountInfo SET bio = :bio WHERE id = :id")
    void updateBio(@Param("id") Long id, @Param("bio") String bio);

    @Query("SELECT a FROM AccountInfo a WHERE a.account.id = :id")
    AccountInfo getBio(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE AccountInfo SET avatar = :url WHERE account.id = :id")
    void updateAvatar(@Param("id") Long id, @Param("url") String url);

    @Modifying
    @Transactional
    @Query("UPDATE AccountInfo SET coverPhoto = :url WHERE account.id = :id")
    void updateCoverPhoto(@Param("id") Long id, @Param("url") String url);

    @Modifying
    @Transactional
    @Query("UPDATE AccountInfo SET firstName = :firstName, lastName = :lastName, fullName = :fullName WHERE id = :id")
    void updateName(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("fullName") String fullName);

    @Modifying @Transactional @Query("DELETE FROM AccountInfo WHERE account.id = :id")
    void deleteAccountMain(Long id);
}
