package com.example.myproj.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

/**
 * MediaTypeUtils.
 *
 * @author Evgeny_Ageev
 */
@UtilityClass
public final class MediaTypeUtils {

    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
