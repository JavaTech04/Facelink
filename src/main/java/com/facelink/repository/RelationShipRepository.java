package com.facelink.repository;

import com.facelink.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationShipRepository extends JpaRepository<Relationship, Integer> {
}
