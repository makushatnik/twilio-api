package com.example.myproj.mapper;

import static com.example.myproj.util.CommonConstants.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import com.example.myproj.dto.sms.TokenDto;
import com.example.myproj.dto.user.UserDto;
import com.example.myproj.dto.user.UserResponse;
import com.example.myproj.model.User;
import org.mapstruct.Mapper;

/**
 * UserMapper.
 *
 * @author Evgeny_Ageev
 */
@Mapper(
        unmappedTargetPolicy = IGNORE,
        componentModel = SPRING
)
public interface UserMapper {

    User toModel(UserDto dto);

    UserResponse toResponse(User user);

    TokenDto toTokenDto(UserResponse dto);
}
