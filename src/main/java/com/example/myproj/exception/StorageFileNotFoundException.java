package com.example.myproj.exception;

/**
 * StorageFileNotFoundException.
 *
 * @author Evgeny_Ageev
 */
public class StorageFileNotFoundException extends RuntimeException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
