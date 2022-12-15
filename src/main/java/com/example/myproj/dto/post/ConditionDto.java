package com.example.myproj.dto.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class ConditionDto implements Serializable {

    /**
     * Condition name.
     */
    @NotBlank
    @ApiModelProperty(example = "BROKEN")
    private String name;

    /**
     * Condition description.
     */
    @ApiModelProperty(example = "Needs repair")
    private String description;
}
