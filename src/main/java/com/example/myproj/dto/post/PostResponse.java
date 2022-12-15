package com.example.myproj.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.myproj.dto.ImageDto;
import com.example.myproj.enums.PostState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PostResponse.
 *
 * @author Evgeny_Ageev
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class PostResponse extends PostDto {

    @EqualsAndHashCode.Include
    private Long id;

    private Long userId;

    private String userFirstName;

    private Long claimedUserId;

    private PostState state;

    private Long swipeLeftCount;

    private Long swipeRightCount;

    private Integer viewCount;

    private Integer shareCount;

    private Integer messageCount;

    private Integer reportCount;

    @JsonProperty("creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime createdAt;

    @JsonProperty("lastUpdatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime updatedAt;

    @JsonProperty("renewDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime renewAt;

    @ApiModelProperty(allowEmptyValue = true, hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deletedAt;

    private List<ImageDto> images;
}
