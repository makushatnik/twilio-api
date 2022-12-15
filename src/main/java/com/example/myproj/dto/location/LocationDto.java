package com.example.myproj.dto.location;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * LocationDto.
 *
 * @author Evgeny_Ageev
 */
@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class LocationDto implements Serializable {

    @NotBlank
    private String address;

    @NotNull
    private MapView mapView;
}
