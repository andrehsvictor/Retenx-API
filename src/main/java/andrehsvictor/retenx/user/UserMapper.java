package andrehsvictor.retenx.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import andrehsvictor.retenx.keycloak.user.KeycloakUser;
import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
import andrehsvictor.retenx.user.dto.PutUserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    GetMeDto userToGetMeDto(User user);

    User postUserDtoToUser(PostUserDto postUserDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromPutUserDto(PutUserDto putUserDto, @MappingTarget User user);

    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "id", ignore = true)
    User keycloakUserToUser(KeycloakUser keycloakUser);

}
