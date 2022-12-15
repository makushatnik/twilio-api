package com.example.myproj.model;

import com.example.myproj.model.post.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Thread {

    private static final String SEQUENCE_NAME = "location_id_seq";

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @JoinColumn(name = "buyer_id", nullable = false)
    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    private User buyer;

    @JoinColumn(name = "seller_id", nullable = false)
    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    private User seller;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(targetEntity = Post.class, fetch = LAZY)
    private Post post;

    private String sid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
