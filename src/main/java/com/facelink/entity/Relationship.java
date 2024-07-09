package com.facelink.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "relationships")
public class Relationship {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status_name", length = 100)
    private String statusName;

}