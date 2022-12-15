package com.example.myproj.dto.location;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class LocationResponse extends LocationDto {

    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty("creationDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime createdAt;

    @JsonProperty("lastUpdatedDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(example = "2021-03-18T21:39:11")
    private LocalDateTime updatedAt;
}
