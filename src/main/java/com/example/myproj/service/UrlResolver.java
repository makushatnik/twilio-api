package com.example.myproj.service;

import com.example.myproj.model.Image;
import com.example.myproj.model.post.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.example.myproj.util.UrlConstants.IMAGE_PATH;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class UrlResolver {

    @Value("${app.serverAddr}")
    private String serverAddr;

    public String getImageUrl(Image image) {
        String fileName = image.getFileName();
        if (isEmpty(fileName)) {
            return null;
        }
        Post post = image.getPost();
        if (isNull(post) || isNull(post.getId())) {
            return null;
        }

        return serverAddr + IMAGE_PATH + post.getId() + "/" + fileName;
    }
}
