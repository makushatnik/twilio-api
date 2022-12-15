package com.example.myproj.dto.sms;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RegisterDto extends LoginDto {

    @NotBlank
    @Size(min = 3)
    private String firstName;

    @NotBlank
    @Size(min = 10)
    private String drive;

}
