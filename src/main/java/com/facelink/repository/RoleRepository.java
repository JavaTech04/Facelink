package com.facelink.repository;

import com.facelink.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("FROM Role WHERE roleName = :name")
    Role findByName(String name);
}
