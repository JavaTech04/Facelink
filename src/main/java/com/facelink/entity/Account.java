package com.facelink.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "password", length = 150)
    private String password;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "verified_account")
    private Boolean verifiedAccount;

    @Column(name = "create_date")
    private Date createDate;

    @OneToOne(mappedBy = "account")
    private AccountDetail accountDetails;

    @OneToOne(mappedBy = "account")
    private AccountInfo accountInfo;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new LinkedHashSet<>();

}