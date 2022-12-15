package com.example.myproj.dto.sms;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class LoginResponse extends LoginDto {

    private boolean exists;

    public LoginResponse(String phone) {
        super(phone);
    }
}
