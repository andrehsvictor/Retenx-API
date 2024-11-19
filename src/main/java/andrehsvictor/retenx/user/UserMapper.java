package andrehsvictor.retenx.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

    User toUser(PostUserDto postUserDto, String keycloakId);

    GetMeDto toGetMeDto(User user, boolean emailVerified);

}
