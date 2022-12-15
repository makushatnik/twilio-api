package com.example.myproj.mapper;

import static com.example.myproj.util.CommonConstants.SPRING;
import static java.util.Objects.isNull;
import static org.mapstruct.ReportingPolicy.IGNORE;

import com.example.myproj.dto.post.ConditionDto;
import com.example.myproj.dto.post.PostRequest;
import com.example.myproj.dto.post.PostResponse;
import com.example.myproj.enums.Condition;
import com.example.myproj.model.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * PostMapper.
 *
 * @author Evgeny_Ageev
 */
@Mapper(
        unmappedTargetPolicy = IGNORE,
        componentModel = SPRING,
        uses = { LocationMapper.class, ImageMapper.class }
)
public interface PostMapper {

    @Mapping(target = "location", ignore = true)
    @Mapping(target = "category", source = "stuffCategory")
    @Mapping(target = "condition", source = "condition.name")
    Post toModel(PostRequest dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "claimedUserId", source = "claimedUser.id")
    @Mapping(target = "stuffCategory", source = "category")
    @Mapping(target = "userFirstName", source = "user.firstName")
    @Mapping(target = "condition", source = "post", qualifiedByName = "getConditionDto")
    @Mapping(target = "location", source = "post.location", qualifiedByName = "toDto")
    PostResponse toResponse(Post post);

    @Named("getConditionDto")
    default ConditionDto getConditionDto(Post post) {
        Condition condition = post.getCondition();
        if (isNull(condition)) {
            return null;
        }

        return new ConditionDto()
                .setName(condition.name())
                .setDescription(condition.getDescription());
    }
}
