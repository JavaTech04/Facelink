package com.facelink.entity;

import com.facelink.enums.PostAudience;
import com.facelink.enums.TypePost;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", length = 30)
    @Enumerated(EnumType.STRING)
    private TypePost type;

    @Lob
    @Column(name = "content")
    private String content;

    @Lob
    @Column(name = "url_image")
    private String urlImage;

    @Lob
    @Column(name = "url_video")
    private String urlVideo;

    @Column(name = "post_audience", length = 20)
    @Enumerated(EnumType.STRING)
    private PostAudience postAudience;


    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "post")
    private Set<Reaction> reactions = new LinkedHashSet<>();

}