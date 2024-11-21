package andrehsvictor.retenx.keycloak.user;

import java.util.List;
import java.util.Optional;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
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

    public KeycloakUser save(KeycloakUser keycloakUser) {
        if (keycloakUser.getId() != null) {
            keycloakUser.update();
            return keycloakUser;
        }
        Response response = usersResource.create(keycloakUser.toRepresentation());
        switch (response.getStatus()) {
            case 201:
                break;
            case 409:
                throw new RetenxException(HttpStatus.CONFLICT, "User already exists.");
            default:
                throw new RetenxException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user.");
        }
        String id = CreatedResponseUtil.getCreatedId(response);
        return new KeycloakUser(getUserResourceById(id));
    }

    public void delete(KeycloakUser keycloakUser) {
        if (keycloakUser.getId() == null) {
            throw new RetenxException(HttpStatus.INTERNAL_SERVER_ERROR, "User ID is null.");
        }
        keycloakUser.delete();
    }

    public void deleteById(String id) {
        getUserResourceById(id).remove();
    }

    public Optional<KeycloakUser> findById(String id) {
        return findBy("id", id);
    }

    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    public Optional<KeycloakUser> findByUsername(String username) {
        return findBy("username", username);
    }

    public Optional<KeycloakUser> findByEmail(String email) {
        return findBy("email", email);
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    private Optional<KeycloakUser> findBy(String key, String value) {
        String query = key + ":" + value;
        List<UserRepresentation> userRepresentations;
        if (key.equals("email")) {
            userRepresentations = usersResource.searchByEmail(value, true);
        }
        userRepresentations = usersResource.search(query, 0, 1);
        if (userRepresentations.isEmpty()) {
            return Optional.empty();
        }
        UserRepresentation userRepresentation = userRepresentations.getFirst();
        KeycloakUser keycloakUser = new KeycloakUser(getUserResourceById(userRepresentation.getId()));
        return Optional.of(keycloakUser);
    }

    private UserResource getUserResourceById(String id) {
        return usersResource.get(id);
    }
}
