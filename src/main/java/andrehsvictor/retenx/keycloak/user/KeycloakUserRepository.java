package andrehsvictor.retenx.keycloak.user;

import java.util.List;
import java.util.Optional;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import andrehsvictor.retenx.exception.RetenxException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class KeycloakUserRepository {

    private final UsersResource usersResource;
    private final KeycloakUserMapper keycloakUserMapper;

    public KeycloakUser save(KeycloakUser keycloakUser) {
        UserRepresentation userRepresentation = keycloakUserMapper.keycloakUserToUserRepresentation(keycloakUser);
        if (!(keycloakUser.getId() == null)) {
            usersResource.get(keycloakUser.getId()).update(userRepresentation);
            return findById(keycloakUser.getId()).get();
        }
        Response response = usersResource.create(userRepresentation);
        switch (response.getStatus()) {
            case 201:
                break;
            case 409:
                throw new RetenxException(HttpStatus.CONFLICT, "User already exists.");
            default:
                throw new RetenxException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user.");
        }
        String id = CreatedResponseUtil.getCreatedId(response);
        userRepresentation = usersResource.get(id).toRepresentation();
        return keycloakUserMapper.userRepresentationToKeycloakUser(userRepresentation);
    }

    public Optional<KeycloakUser> findById(String id) {
        String search = "id:" + id;
        List<UserRepresentation> userRepresentations = usersResource.search(search, 0, 1);
        if (userRepresentations.isEmpty()) {
            return Optional.empty();
        }
        UserRepresentation userRepresentation = userRepresentations.getFirst();
        KeycloakUser keycloakUser = keycloakUserMapper.userRepresentationToKeycloakUser(userRepresentation);
        return Optional.of(keycloakUser);
    }

    public Optional<KeycloakUser> findByUsername(String username) {
        String search = "username:" + username;
        List<UserRepresentation> userRepresentations = usersResource.search(search, 0, 1);
        if (userRepresentations.isEmpty()) {
            return Optional.empty();
        }
        UserRepresentation userRepresentation = userRepresentations.getFirst();
        KeycloakUser keycloakUser = keycloakUserMapper.userRepresentationToKeycloakUser(userRepresentation);
        return Optional.of(keycloakUser);
    }

    public void deleteById(String id) {
        usersResource.get(id).remove();
    }

    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    public Optional<KeycloakUser> findByEmail(String email) {
        String search = "email:" + email;
        List<UserRepresentation> userRepresentations = usersResource.search(search, 0, 1);
        if (userRepresentations.isEmpty()) {
            return Optional.empty();
        }
        UserRepresentation userRepresentation = userRepresentations.getFirst();
        KeycloakUser keycloakUser = keycloakUserMapper.userRepresentationToKeycloakUser(userRepresentation);
        return Optional.of(keycloakUser);
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

}
