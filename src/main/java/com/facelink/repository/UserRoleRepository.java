package com.facelink.repository;

import com.facelink.entity.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Modifying @Transactional @Query("DELETE FROM UserRole WHERE account.id = :id")
    void deleteAccountMain(Long id);
}
