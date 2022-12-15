package com.example.myproj.dto.pick;

import com.example.myproj.enums.PostState;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class PostView implements Serializable {

    private Long id;

    private String title;

    private PostState state;

    private Long ownerId;

    private String ownerName;

    private Long receivedUserId;

    private Integer swipeRightCount;

    private Integer threadCount;
}
