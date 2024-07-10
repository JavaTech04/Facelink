package com.facelink.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "listFriends")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_info")
    private Account friendInfo;

    @Column(name = "status")
    private Integer status;

}