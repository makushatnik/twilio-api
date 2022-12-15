package com.example.myproj.model;

import com.example.myproj.enums.PostState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "pick_view")
public class PickView {

    /**
     * Like id.
     */
    @Id
    private Long id;

    private Long postId;

    private Long whoLikedId;

    private String whoLikedName;

    private LocalDateTime likeCreatedAt;

    private String title;

    @Enumerated(STRING)
    private PostState state;

    private Long ownerId;

    private String ownerName;

    private Integer threadCount;

    private Integer swipeRightCount;

    private Long receivedUserId;

    private Long threadId;

    private LocalDateTime threadCreatedAt;

    private Long sellerId;

    private Long buyerId;

    @JoinColumn(name = "post_id")
    @OneToMany(targetEntity = Image.class, fetch = LAZY)
    private List<Image> images;
}
