package com.example.myproj.controller;

import com.example.myproj.controller.api.LoginControllerAPI;
import com.example.myproj.dto.sms.*;
import com.example.myproj.dto.user.UserResponse;
import com.example.myproj.exception.sms.SMSCodeExpiredException;
import com.example.myproj.exception.sms.SMSCodeInvalidException;
import com.example.myproj.exception.sms.SMSCodeWasNotRequestedException;
import com.example.myproj.model.User;
import com.example.myproj.security.AuthenticatedUser;
import com.example.myproj.service.LoginService;
import com.example.myproj.service.TokenProvider;
import com.example.myproj.service.UserService;
import com.twilio.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Collections;

import static com.example.myproj.util.CommonConstants.ROLE_USER;
import static com.example.myproj.util.CommonUtils.sendErrors;
import static java.util.Objects.isNull;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController implements LoginControllerAPI {

    private static final String SMS_CODE_IS_EXPIRED = "SMS Code is expired already. Try new one";
    private static final String SMS_CODE_IS_INVALID = "SMS Code is invalid";
    private static final String SMS_CODE_WAS_NOT_REQUESTED = "SMS Code wasn't requested";

    private final UserService userService;
    private final LoginService loginService;
    private final TokenProvider tokenProvider;

    @Value("${app.sms.expireIn}")
    private Integer expireIn;

    @Value("${app.sms.digits}")
    private Integer digits;

    @Override
    public ResponseEntity<LoginResponse> loginRequest(@RequestBody @Valid LoginDto loginDto, HttpSession session,
                                                      Errors errors) {
        sendErrors(errors);
        String phone = loginDto.getPhone();
        User user = userService.getByPhone(phone);
        LoginResponse loginResponse = new LoginResponse(phone)
                .setExists(!isNull(user));
        saveCodeAndSendSMS(phone, session);
        return ok(loginResponse);
    }

    @Override
    public ResponseEntity<?> loginConfirm(@RequestBody @Valid ConfirmRequest confirmDto, HttpSession session,
                                          Errors errors) {
        sendErrors(errors);
        String phone = confirmDto.getPhone();
        String receivedCode = confirmDto.getCode();
        //check sms code
        checkSMSCodeInvalidOrExpired(phone, receivedCode, session);
        //check if we need to register user
        User user = userService.getByPhone(phone);
        if (isNull(user)) {
            LoginResponse loginResponse = new LoginResponse(phone)
                    .setExists(false);
            return ok(loginResponse);
        }

        return ok(createToken(userService.getTokenDto(phone), session));
    }

    @Override
    public ResponseEntity<TokenDto> registerRequest(@RequestBody @Valid RegisterDto dto, HttpSession session,
                                                    Errors errors) {
        sendErrors(errors);
        return ok(createToken(userService.register(dto), session));
    }

    private TokenDto createToken(TokenDto tokenDto, HttpSession session) {
        String token = (String) session.getAttribute(tokenDto.getFirstName() + "-token");
        if (StringUtils.isEmpty(token)) {
            token = tokenProvider.buildToken(session.getId(), getPrincipal(tokenDto));
            session.setAttribute(tokenDto.getFirstName() + "-token", token);
        }
        tokenDto.setToken(token);
        return tokenDto;
    }

    private void checkSMSCodeInvalidOrExpired(String phone, String receivedCode, HttpSession session) {
        SMSDto sms = (SMSDto) session.getAttribute(phone);
        if (isNull(sms)) {
            log.error(SMS_CODE_WAS_NOT_REQUESTED);
            throw new SMSCodeWasNotRequestedException(SMS_CODE_WAS_NOT_REQUESTED);
        }
        if (sms.isExpired()) {
            log.error(SMS_CODE_IS_EXPIRED);
            throw new SMSCodeExpiredException(SMS_CODE_IS_EXPIRED);
        }
        if (!sms.getCode().equalsIgnoreCase(receivedCode)) {
            log.error(SMS_CODE_IS_INVALID, receivedCode);
            throw new SMSCodeInvalidException(SMS_CODE_IS_INVALID);
        }
        session.removeAttribute(phone);
    }

    private AuthenticatedUser getPrincipal(UserResponse userDto) {
        return new AuthenticatedUser()
                .setId(userDto.getId())
                .setUserName(userDto.getFirstName())
                .setPhone(userDto.getPhoneNumber())
                .setAuthorities(Collections.singleton(new SimpleGrantedAuthority(ROLE_USER)));
    }

    private void saveCodeAndSendSMS(String phone, HttpSession session) {
        try {
            SMSDto sms = createSMSDto(phone);
            session.setAttribute(phone, sms);
            loginService.send(sms);
        } catch (ApiException e) {
            log.error(e.toString(), e);
        }
    }

    private SMSDto createSMSDto(String phone) {
        String code = RandomStringUtils.randomNumeric(digits);
        return new SMSDto(phone, code, expireIn);
    }
}
