package com.example.myproj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

/**
 * ImageDto.
 *
 * @author Evgeny_Ageev
 */
@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ImageDto implements Comparable<ImageDto>, Serializable {

    @EqualsAndHashCode.Include
    private Long id;

    private Long postId;

    private String url;

    @JsonProperty("validatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime validatedAt;

    @JsonProperty("creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime createdAt;

    @JsonProperty("lastUpdatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime updatedAt;

    @Override
    public int compareTo(ImageDto o) {
        if (isNull(getCreatedAt())) {
            if (isNull(o.getCreatedAt())) {
                return 0;
            } else {
                return -1;
            }
        }
        return getCreatedAt().compareTo(o.getCreatedAt());
    }
}
