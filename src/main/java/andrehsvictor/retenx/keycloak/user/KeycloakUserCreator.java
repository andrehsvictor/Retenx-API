package andrehsvictor.retenx.keycloak.user;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import andrehsvictor.retenx.user.User;

@Mapper(componentModel = "spring", imports = { List.class })
public interface KeycloakUserCreator {

    @Mapping(target = "emailVerified", constant = "true")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "realmRoles", expression = "java(List.of(\"user\"))")
    @Mapping(target = "requiredActions", expression = "java(List.of(\"VERIFY_EMAIL\"))")
    KeycloakUser create(User user);

}
