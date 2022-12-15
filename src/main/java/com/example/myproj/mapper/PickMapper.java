package com.example.myproj.mapper;

import com.example.myproj.dto.pick.PickDto;
import com.example.myproj.model.Image;
import com.example.myproj.model.PickView;
import com.example.myproj.model.post.TempLike;
import com.example.myproj.service.UrlResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.myproj.util.CommonConstants.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Mapper(
        unmappedTargetPolicy = IGNORE,
        componentModel = SPRING
)
public abstract class PickMapper {

    @Autowired
    private UrlResolver urlResolver;

    @Mapping(target = "imgUrl", source = "pick.images", qualifiedByName = "getFirstImgUrl")
    @Mapping(target = "postView.id", source = "postId")
    @Mapping(target = "postView.title", source = "title")
    @Mapping(target = "postView.swipeRightCount", source = "swipeRightCount")
    @Mapping(target = "postView.state", source = "state")
    @Mapping(target = "postView.ownerId", source = "ownerId")
    @Mapping(target = "postView.ownerName", source = "ownerName")
    @Mapping(target = "postView.receivedUserId", source = "receivedUserId")
    @Mapping(target = "postView.threadCount", source = "threadCount")
    @Mapping(target = "thread.id", source = "threadId")
    @Mapping(target = "thread.sellerId", source = "sellerId")
    @Mapping(target = "thread.buyerId", source = "buyerId")
    @Mapping(target = "thread.createdAt", source = "threadCreatedAt")
    public abstract PickDto toDto(PickView pick);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imgUrl", source = "temp.post.images", qualifiedByName = "getFirstImgUrl")
    @Mapping(target = "thread.id", ignore = true)
    @Mapping(target = "thread.sellerId", ignore = true)
    @Mapping(target = "thread.buyerId", ignore = true)
    @Mapping(target = "thread.createdAt", ignore = true)
    @Mapping(target = "likeCreatedAt", ignore = true)
    @Mapping(target = "whoLikedId", ignore = true)
    @Mapping(target = "whoLikedName", ignore = true)
    @Mapping(target = "postView.id", source = "post.id")
    @Mapping(target = "postView.title", source = "post.title")
    @Mapping(target = "postView.swipeRightCount", source = "post.swipeRightCount")
    @Mapping(target = "postView.state", source = "post.state")
    @Mapping(target = "postView.ownerId", source = "post.user.id")
    @Mapping(target = "postView.ownerName", source = "post.user.firstName")
    @Mapping(target = "postView.receivedUserId", source = "post.claimedUser.id")
    @Mapping(target = "postView.threadCount", source = "post.threadCount")
    public abstract PickDto toDtoFromTemp(TempLike temp);

    @Named("getFirstImgUrl")
    public String getFirstImgUrl(List<Image> images) {
        if (isEmpty(images)) {
            return null;
        }

        return urlResolver.getImageUrl(images.get(0));
    }
}
