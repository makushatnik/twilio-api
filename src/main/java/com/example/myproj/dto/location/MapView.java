package com.example.myproj.dto.location;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class MapView implements Serializable {

    @NotBlank
    @ApiModelProperty(example = "39.639538")
    private String latitude;

    @NotBlank
    @ApiModelProperty(example = "-112.502582")
    private String longitude;
}
