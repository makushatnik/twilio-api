package com.example.myproj.mapper;

import static com.example.myproj.util.CommonConstants.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import com.example.myproj.dto.ImageDto;
import com.example.myproj.model.Image;
import com.example.myproj.service.UrlResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ImageMapper.
 *
 * @author Evgeny_Ageev
 */
@Mapper(
        unmappedTargetPolicy = IGNORE,
        componentModel = SPRING
)
public abstract class ImageMapper {

    @Autowired
    private UrlResolver urlResolver;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "fileName", ignore = true)
    public abstract Image toModel(ImageDto dto);

    @Mapping(target = "url", source = "image", qualifiedByName = "gatherUrl")
    @Mapping(target = "postId", source = "post.id")
    public abstract ImageDto toDto(Image image);

    @Named("gatherUrl")
    public String gatherUrl(Image image) {
        return urlResolver.getImageUrl(image);
    }
}
