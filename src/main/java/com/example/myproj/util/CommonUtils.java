package com.example.myproj.util;

import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.Objects.isNull;

@UtilityClass
public final class CommonUtils {

    private static final String NEGATIVE_OR_ZERO_ID_ERROR = "Id can be only a positive number";
    private static final String NEGATIVE_OR_ZERO_NUM_ERROR = "Integer can be only a positive number";
    private static final String STRING_SHOULD_BE_NOT_EMPTY = "String should be not empty";
    private static final String VALUE_IS_NULL_ERROR = "Value should be not null";
    private static final String LIST_IS_EMPTY_ERROR = "List is empty";
    private static final String FILE_IS_EMPTY = "File is empty";
    private static final String FILES_ARRAY_IS_EMPTY = "Array of files is empty";

    public static void checkIfLongIdIsPositive(Long num) {
        if (num <= 0) {
            throw new IllegalArgumentException(NEGATIVE_OR_ZERO_ID_ERROR);
        }
    }

    public static void checkIfIntegerIsPositive(Integer num) {
        if (num <= 0) {
            throw new IllegalArgumentException(NEGATIVE_OR_ZERO_NUM_ERROR);
        }
    }

    public static void checkIfStringIsBlank(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(STRING_SHOULD_BE_NOT_EMPTY);
        }
    }

    public static void checkIfValueIsNotNull(Object o) {
        if (isNull(o)) {
            throw new IllegalArgumentException(VALUE_IS_NULL_ERROR);
        }
    }

    public static void checkIfListIsEmpty(List list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException(LIST_IS_EMPTY_ERROR);
        }
    }

    public static void checkIfFileIsEmpty(MultipartFile file) {
        if (isNull(file) || file.isEmpty()) {
            throw new IllegalArgumentException(FILE_IS_EMPTY);
        }
    }

    public static void  checkIfArrayOfFilesIsEmpty(MultipartFile[] files) {
        if (isNull(files) || files.length == 0) {
            throw new IllegalArgumentException(FILES_ARRAY_IS_EMPTY);
        }
    }

    public static void sendErrors(Errors errors) {
        if (errors.hasErrors()) {
            StringBuilder summary = new StringBuilder();
            errors.getAllErrors().forEach(error -> {
                if (!ArrayUtils.isEmpty(error.getCodes())) {
                    summary.append(Objects.requireNonNull(error.getCodes())[0]).append(":").append(error.getDefaultMessage()).append("\r\n");
                }
            });
            throw new IllegalArgumentException(summary.toString());
        }
    }
}
