package com.facelink.repository;

import com.facelink.entity.AccountDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM AccountDetail WHERE account.id = :id")
    void deleteAccountMain(Long id);
}
