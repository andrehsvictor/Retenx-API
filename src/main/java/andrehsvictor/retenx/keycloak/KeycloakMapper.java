package andrehsvictor.retenx.keycloak;

import java.util.List;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants.ComponentModel;

import andrehsvictor.retenx.user.dto.PostUserDto;
import andrehsvictor.retenx.user.dto.PutUserDto;

@Mapper(componentModel = ComponentModel.SPRING)
public interface KeycloakMapper {

    UserRepresentation toUserRepresentation(PostUserDto postUserDto);

    @AfterMapping
    default void afterToUserRepresentation(PostUserDto postUserDto,
            @MappingTarget UserRepresentation userRepresentation) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(postUserDto.getPassword());
        credentialRepresentation.setTemporary(false);

        userRepresentation.setCredentials(List.of(credentialRepresentation));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserRepresentation updateFromPutUserDto(PutUserDto putUserDto, @MappingTarget UserRepresentation userRepresentation);

}
