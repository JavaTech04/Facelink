package com.facelink.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "accountDetail")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"account"})
public class AccountDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "followers")
    private Integer followers;

    @Column(name = "following")
    private Integer following;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

}