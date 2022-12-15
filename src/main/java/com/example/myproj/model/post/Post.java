package com.example.myproj.model.post;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import com.example.myproj.enums.Category;
import com.example.myproj.enums.Condition;
import com.example.myproj.enums.PostState;
import com.example.myproj.model.Image;
import com.example.myproj.model.Location;
import com.example.myproj.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Post.
 *
 * @author Evgeny_Ageev
 */
@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "deleted_at IS NULL")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    public static final String FN_ID = "id";
    public static final String FN_LOCATION = "location";
    public static final String FN_STATE = "state";
    public static final String FN_CATEGORY = "category";
    public static final String FN_CONDITION = "condition";
    public static final String FN_RENEW_AT = "renewAt";

    private static final String SEQUENCE_NAME = "post_id_seq";

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    private String title;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    private User user;

    @JoinColumn(name = "location_id", nullable = false)
    @ManyToOne(targetEntity = Location.class, fetch = LAZY)
    private Location location;

    private String description;

    @Enumerated(STRING)
    private Category category;

    @Enumerated(STRING)
    private Condition condition;

    @Enumerated(STRING)
    private PostState state;

    @JoinColumn(name = "claimed_user_id")
    @ManyToOne(targetEntity = User.class, fetch = LAZY)
    private User claimedUser;

    private Long swipeLeftCount;

    private Long swipeRightCount;

    private Integer viewCount;

    private Integer shareCount;

    private Integer threadCount;

    private Integer messageCount;

    private Integer reportCount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime renewAt;

    private LocalDateTime deletedAt;

    @JoinColumn(name = "post_id")
    @OneToMany(targetEntity = Image.class, fetch = LAZY)
    private List<Image> images;
}
