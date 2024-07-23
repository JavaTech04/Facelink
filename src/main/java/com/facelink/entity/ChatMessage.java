package com.facelink.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "chatMessage")
public class ChatMessage {
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

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private Date timestamp;

}