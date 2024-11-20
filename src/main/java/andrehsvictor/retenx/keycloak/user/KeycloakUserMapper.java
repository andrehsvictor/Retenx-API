package andrehsvictor.retenx.keycloak.user;

import java.util.List;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface KeycloakUserMapper {

    UserRepresentation keycloakUserToUserRepresentation(KeycloakUser keycloakUser);

    @AfterMapping
    default void afterMapping(KeycloakUser keycloakUser, @MappingTarget UserRepresentation userRepresentation) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(keycloakUser.getPassword());
        credential.setTemporary(false);
        userRepresentation.setCredentials(List.of(credential));

        userRepresentation.singleAttribute("avatarUrl", keycloakUser.getAvatarUrl());
    }

    @Mapping(target = "avatarUrl", expression = "java(userRepresentation.firstAttribute(\"avatarUrl\"))")
    @Mapping(target = "password", expression = "java(userRepresentation.getCredentials().getFirst().getValue())")
    KeycloakUser userRepresentationToKeycloakUser(UserRepresentation userRepresentation);

}
