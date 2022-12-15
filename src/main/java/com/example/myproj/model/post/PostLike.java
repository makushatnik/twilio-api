package com.example.myproj.model.post;

import com.example.myproj.enums.PostRate;
import com.example.myproj.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostLike {

    public static final String FN_POST = "post";
    public static final String FN_USER = "user";

    private static final String SEQUENCE_NAME = "post_like_id_seq";

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(targetEntity = Post.class, fetch = LAZY)
    private Post post;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    private User user;

    @Enumerated(STRING)
    private PostRate rate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
