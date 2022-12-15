package com.example.myproj.service;

import com.example.myproj.dto.sms.SMSDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {

    @Value("${app.twilio.account-sid}")
    private String accountSid;

    @Value("${app.twilio.auth-token}")
    private String authToken;

    @Value("${app.twilio.phone-number}")
    private String phoneNumber;

    public void send(SMSDto sms) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(phoneNumber), sms.getCode())
                                 .create();
        log.info(message.getSid());
    }
}
