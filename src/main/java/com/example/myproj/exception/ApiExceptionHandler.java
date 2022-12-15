package com.example.myproj.exception;

import static com.example.myproj.util.CommonConstants.BODY;
import static com.example.myproj.util.CommonConstants.MESSAGE;
import static com.example.myproj.util.EnvironmentUtils.prodMode;

import com.example.myproj.exception.sms.SMSCodeInvalidException;
import com.example.myproj.exception.sms.SMSCodeWasNotRequestedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiExceptionHandler.
 *
 * @author Evgeny_Ageev
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    private final Environment environment;

    @Autowired
    public ApiExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handleException(Exception ex) {
        return process(ex);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Map<String, String> handleAccessDeniedException(Exception ex) {
        return process(ex);
    }

    @ExceptionHandler(
            value = {
                    NoHandlerFoundException.class,
                    EntityNotFoundException.class,
                    StorageFileNotFoundException.class
            }
    )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> handleNotFoundException(Exception ex) {
        return process(ex);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            InvalidTokenDataException.class,
            UnsupportedMediaTypeStatusException.class,
            SMSCodeInvalidException.class,
            SMSCodeWasNotRequestedException.class,
            AlreadyRegisteredException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleIllegalArgumentException(Exception ex) {
        return process(ex);
    }

    private Map<String, String> process(Exception ex) {
        Map<String, String> result = new HashMap<>();
        if (prodMode(environment)) {
            result.put(MESSAGE, ex.toString());
        } else {
            result.put(MESSAGE, ex.toString());
            result.put(BODY, ExceptionUtils.getStackTrace(ex));
        }
        log.error(ex.getMessage(), ex);

        return result;
    }
}
