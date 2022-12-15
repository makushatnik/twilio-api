package com.example.myproj.util;

import com.example.myproj.dto.post.PostResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.springframework.data.domain.Sort.Direction.DESC;

@UtilityClass
public class PostUtils {

    public static Sort getPostDefaultSort() {
        return Sort.by(DESC, "renewAt");
    }

    public static Sort getPickDefaultSort() {
        return Sort.by(DESC, "likeCreatedAt");
    }

    public static PostResponse sorted(PostResponse postDto) {
        Collections.sort(postDto.getImages());
        return postDto;
    }
}
