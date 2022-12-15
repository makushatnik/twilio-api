package com.example.myproj.exception.sms;

public class SMSCodeExpiredException extends RuntimeException {

    public SMSCodeExpiredException(String description) {
        super(description);
    }

    public SMSCodeExpiredException(String description, Throwable t) {
        super(description, t);
    }
}
