package com.facelink.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "chatRoom")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 200)
    @Column(name = "chat_name", length = 200)
    private String chatName;

    @Column(name = "senderId")
    private Long senderId;

    @Column(name = "recipientId")
    private Long recipientId;

}