package com.example.myproj.dto.chat;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class MessageDto implements Serializable {

    @NotNull @Positive
    private Long postId;

    @NotBlank
    private String text;
}
