package com.facelink.repository;

import com.facelink.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
