package andrehsvictor.retenx.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants.ComponentModel;

import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
import andrehsvictor.retenx.user.dto.PutUserDto;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

    User toUser(PostUserDto postUserDto, String keycloakId);

    GetMeDto toGetMeDto(User user, boolean emailVerified);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateFromPutUserDto(PutUserDto putUserDto, @MappingTarget User user);

}
