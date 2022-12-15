package com.example.myproj.mapper;

import com.example.myproj.dto.chat.MessageResponse;
import com.example.myproj.dto.chat.ThreadDto;
import com.example.myproj.model.Thread;
import com.twilio.rest.conversations.v1.conversation.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static com.example.myproj.util.CommonConstants.SPRING;
import static java.util.Objects.isNull;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
        unmappedTargetPolicy = IGNORE,
        componentModel = SPRING
)
public interface ThreadMapper {

    Thread toModel(ThreadDto dto);

    ThreadDto toDto(Thread thread);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "text", source = "body")
    @Mapping(target = "createdAt", source = "message", qualifiedByName = "getFromZonedTime")
    MessageResponse toMessageResponse(Message message);

    @Named("getFromZonedTime")
    default LocalDateTime getFromZonedTime(Message message) {
        ZonedDateTime dateCreated = message.getDateCreated();
        if (isNull(dateCreated)) {
            return null;
        }

        return dateCreated.toLocalDateTime();
    }
}
