package com.example.myproj.model.post;

import com.example.myproj.enums.PostRate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TempLike {

    private static final String SEQUENCE_NAME = "temp_like_id_seq";

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @JoinColumn(name = "post_id", nullable = false)
    @ManyToOne(targetEntity = Post.class, fetch = LAZY)
    private Post post;

    private String drive;

    @Enumerated(STRING)
    private PostRate rate;
}
