package com.example.myproj.exception.sms;

public class SMSCodeWasNotRequestedException extends RuntimeException {

    public SMSCodeWasNotRequestedException(String description) {
        super(description);
    }

    public SMSCodeWasNotRequestedException(String description, Throwable t) {
        super(description, t);
    }
}
