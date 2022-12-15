package com.example.myproj.dto.chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MessageRequest extends MessageDto {

    /**
     * User To whom we are writing.
     */
    @NotNull @Positive
    private Long userId;
}
