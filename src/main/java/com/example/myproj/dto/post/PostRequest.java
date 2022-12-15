package com.example.myproj.dto.post;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * PostRequest.
 *
 * @author Evgeny_Ageev
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PostRequest extends PostDto {

    private List<Long> imageIds;
}
