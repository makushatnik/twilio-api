package com.example.myproj.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * UserDto.
 *
 * @author Evgeny_Ageev
 */
@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto implements Serializable {

    @NotBlank
    @ApiModelProperty(example = "John Doe")
    private String firstName;

    @ApiModelProperty(example = "john.doe@example.com")
    private String email;

    @ApiModelProperty(example = "@johndoe")
    private String twitter;

    @ApiModelProperty(example = "johndoe")
    private String facebook;
}
