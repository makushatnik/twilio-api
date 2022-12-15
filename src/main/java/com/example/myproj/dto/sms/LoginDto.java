package com.example.myproj.dto.sms;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginDto implements Serializable {

    @NotBlank
    @Pattern(regexp = "^[+]?\\d{10,15}$")
    private String phone;

}
