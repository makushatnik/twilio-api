package com.example.myproj.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * FileUtils.
 *
 * @author Evgeny_Ageev
 */
@UtilityClass
public final class FileUtils {

    private static final String INCORRECT_FILE_EXTENSION = "Incorrect file extension: {} %s";

    public static long getResourceLength(Resource resource, Logger log) {
        try {
            return resource.contentLength();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return 0L;
    }

    public static void checkFileExtensionAllowed(Logger log, String extension) {
        if (isEmpty(extension)) {
            log.error(INCORRECT_FILE_EXTENSION, extension);
            throw new UnsupportedMediaTypeStatusException(String.format(INCORRECT_FILE_EXTENSION, extension));
        }
        extension = extension.toLowerCase();
        if (!getAllowedExtensions().contains(extension)) {
            log.error(INCORRECT_FILE_EXTENSION, extension);
            throw new UnsupportedMediaTypeStatusException(String.format(INCORRECT_FILE_EXTENSION, extension));
        }
    }

    public static String cutExtensionFromBase64(String file) {
        int startInd = file.indexOf("data:image/");
        int endInd = file.indexOf(";base64");
        String subStr = file.substring(startInd, endInd).replace("data:image/","");
        if (subStr.startsWith("jpg")) {
            return "jpg";
        } else if (subStr.startsWith("jpeg")) {
            return "jpeg";
        } else if (subStr.startsWith("png")) {
            return "png";
        }
        return EMPTY;
    }

    private static Set<String> getAllowedExtensions() {
        Set<String> set = new HashSet<>();
        set.add("jpg");
        set.add("jpeg");
        set.add("png");
        return set;
    }
}
