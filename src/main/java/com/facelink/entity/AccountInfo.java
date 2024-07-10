package com.facelink.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Entity
@Table(name = "accountInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"account"})
public class AccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "cover_photo", length = 500)
    private String coverPhoto;

    @Column(name = "avatar", length = 500)
    private String avatar;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "other_name", length = 50)
    private String otherName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "education", length = 100)
    private String education;

    @Column(name = "work", length = 100)
    private String work;

    @Column(name = "current_city", length = 100)
    private String currentCity;

    @Column(name = "hometown", length = 150)
    private String hometown;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "relationship_id")
    private Relationship relationship;

}