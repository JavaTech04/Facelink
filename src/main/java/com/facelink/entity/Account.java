package com.facelink.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"accountInfo", "accountDetails"})
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
    private Boolean isLocked = false;

    @Column(name = "is_enabled")
    private Boolean isEnabled = false;

    @Column(name = "verified_account")
    private Boolean verifiedAccount = false;

    @Column(name = "create_date")
    private Date createDate = new Date();

    @OneToOne(mappedBy = "account")
    private AccountDetail accountDetails;

    @OneToOne(mappedBy = "account")
    private AccountInfo accountInfo;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new LinkedHashSet<>();

}