package com.example.myproj.dto.post;

import com.example.myproj.dto.location.LocationDto;
import com.example.myproj.enums.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * PostDto.
 *
 * @author Evgeny_Ageev
 */
@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class PostDto implements Serializable {

    @NotBlank
    @ApiModelProperty(example = "Post title")
    private String title;

    @NotBlank
    @ApiModelProperty(example = "Post description")
    private String description;

    @NotNull
    private Category stuffCategory;

    @NotNull
    private ConditionDto condition;

    @NotNull
    private LocationDto location;
}
