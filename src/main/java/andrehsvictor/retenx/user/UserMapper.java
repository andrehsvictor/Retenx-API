package andrehsvictor.retenx.user;

import java.util.List;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;

import andrehsvictor.retenx.keycloak.user.KeycloakUser;
import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

    KeycloakUser userToKeycloakUser(User user);

    @Mapping(target = "externalId", source = "id")
    User keycloakUserToUser(KeycloakUser keycloakUser);

    User postUserDtoToUser(PostUserDto postUserDto);

    GetMeDto userToGetMeDto(User user, boolean emailVerified);

}
