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
    @Mapping(target = "avatarUrl", conditionExpression = "java(user.getAvatarUrl() != null)")
    @Mapping(target = "email", conditionExpression = "java(user.getEmail() != null)")
    @Mapping(target = "firstName", conditionExpression = "java(user.getFirstName() != null)")
    @Mapping(target = "lastName", conditionExpression = "java(user.getLastName() != null)")
    @Mapping(target = "username", conditionExpression = "java(user.getUsername() != null)")
    KeycloakUser updateKeycloakUserFromUser(User user);

    @AfterMapping
    default void afterMapping(User user, @MappingTarget KeycloakUser keycloakUser) {
        if (user.getEmail() != null && !user.getEmail().equals(keycloakUser.getEmail())) {
            keycloakUser.setEmailVerified(false);
        }
    }

}
