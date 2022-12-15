package com.example.myproj.dto.sms;

import com.example.myproj.dto.user.UserResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@ApiModel
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TokenDto extends UserResponse {

    private String token;

}
