package com.example.myproj.dto.sms;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel
@NoArgsConstructor
@Accessors(chain = true)
public class SMSDto implements Serializable {

    private String to;
    private String code;
    private LocalDateTime expireTime;

    public SMSDto(String to, String code, int expireIn) {
        this.to = to;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public SMSDto(String to, String code, LocalDateTime expireTime) {
        this.to = to;
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
