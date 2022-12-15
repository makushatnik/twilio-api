package com.example.myproj.dto.pick;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.myproj.dto.chat.ThreadDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class PickDto implements Serializable {

    private Long id;

    private PostView postView;

    private Long whoLikedId;

    private String whoLikedName;

    private String imgUrl;

    private ThreadDto thread;

    /**
     * Total thread count for linked post.
     */
    private int threadCount;

    private boolean hasUnread;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime likeCreatedAt;
}
