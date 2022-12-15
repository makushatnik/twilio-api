package com.example.myproj.dto.post;

import com.example.myproj.enums.PostRate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class TempLikeDto implements Serializable {

    @NotNull @Positive
    @ApiModelProperty(example = "1")
    private Long postId;

    @NotBlank
    @Size(min = 10)
    private String drive;

    @NotNull
    private PostRate rate;
}
