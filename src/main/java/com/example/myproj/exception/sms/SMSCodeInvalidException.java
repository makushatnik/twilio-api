package com.example.myproj.exception.sms;

public class SMSCodeInvalidException extends RuntimeException {

    public SMSCodeInvalidException(String description) {
        super(description);
    }

    public SMSCodeInvalidException(String description, Throwable t) {
        super(description, t);
    }
}
