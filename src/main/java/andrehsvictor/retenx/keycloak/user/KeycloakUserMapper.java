package andrehsvictor.retenx.keycloak.user;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import andrehsvictor.retenx.user.User;

@Mapper(componentModel = "spring")
public interface KeycloakUserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    KeycloakUser updateKeycloakUserFromUser(User user, @MappingTarget KeycloakUser keycloakUser);

    @AfterMapping
    default void afterMapping(User user, @MappingTarget KeycloakUser keycloakUser) {
        if (user.getEmail() != null && !user.getEmail().equals(keycloakUser.getEmail())) {
            keycloakUser.setEmailVerified(false);
        }
    }

}
