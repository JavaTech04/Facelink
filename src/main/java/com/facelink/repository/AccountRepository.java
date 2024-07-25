package com.facelink.repository;

import com.facelink.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("FROM Account WHERE (phoneNumber = :data) OR (email = :data)")
    Optional<Account> findAccount(@Param("data") String data);

    @Query("SELECT a.isLocked FROM Account a WHERE a.id = :id")
    Boolean isAccountLocked(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Account SET email = :email, phoneNumber = :phoneNumber, password = :pw WHERE id = :id")
    void updateAccountLogin(
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            @Param("pw") String pw,
            @Param("id") Long id
    );

    @Modifying @Transactional @Query("DELETE FROM Account WHERE id = :id")
    void deleteAccountMain(Long id);

    @Modifying @Transactional @Query("UPDATE Account SET isLocked = true WHERE id = :id")
    void lockAccount(Long id);

    @Modifying @Transactional @Query("UPDATE Account SET isLocked = false WHERE id = :id")
    void unLockAccount(Long id);

    @Modifying @Transactional @Query("UPDATE Account SET verifiedAccount = true WHERE id = :id")
    void verifiedAccount(Long id);

    @Modifying @Transactional @Query("UPDATE Account SET verifiedAccount = false WHERE id = :id")
    void unVerifiedAccount(Long id);
}
