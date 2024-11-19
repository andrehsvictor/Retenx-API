package andrehsvictor.retenx.keycloak;

import java.util.List;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private final RealmResource realmResource;

    private static final String SEARCH_BY_ID = "id:%s";
    private static final String DEFAULT_USER_ROLE = "user";
    private static final String DEFAULT_REQUIRED_ACTION = "VERIFY_EMAIL";

    @Transactional
    public UserRepresentation create(UserRepresentation userRepresentation) {
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);
        userRepresentation.setRealmRoles(List.of(DEFAULT_USER_ROLE));
        userRepresentation.setRequiredActions(List.of(DEFAULT_REQUIRED_ACTION));

        Response response = realmResource.users().create(userRepresentation);
        switch (response.getStatus()) {
            case 201:
                break;
            case 409:
                throw new RetenxException(HttpStatus.CONFLICT, "User already exists in Keycloak.");
            default:
                throw new RetenxException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user in Keycloak.");
        }
        String userId = CreatedResponseUtil.getCreatedId(response);

        return findById(userId);
    }

    @Cacheable(value = "keycloakUser", key = "#id")
    public UserRepresentation findById(String id) {
        String search = String.format(SEARCH_BY_ID, id);
        List<UserRepresentation> users = realmResource.users().search(search, 0, 1);
        if (users.isEmpty()) {
            throw new RetenxException(HttpStatus.NOT_FOUND, "User not found in Keycloak with ID: " + id + ".");
        }
        return users.getFirst();
    }

    @Cacheable(value = "keycloakUser", key = "#id")
    public boolean existsById(String id) {
        String search = String.format(SEARCH_BY_ID, id);
        Integer count = realmResource.users().count(search);
        return count > 0;
    }

    @Cacheable(value = "keycloakUser", key = "#email")
    public boolean isEmailVerified(String email) {
        UserRepresentation user = findByEmail(email);
        return user.isEmailVerified();
    }

    @Cacheable(value = "keycloakUser", key = "#email")
    public UserRepresentation findByEmail(String email) {
        List<UserRepresentation> users = realmResource.users().searchByEmail(email, true);
        if (users.isEmpty()) {
            throw new RetenxException(HttpStatus.NOT_FOUND, "User not found in Keycloak with e-mail: " + email + ".");
        }
        return users.getFirst();
    }

    public void sendVerificationEmail(String email) {
        UserRepresentation user = findByEmail(email);
        if (user.isEmailVerified()) {
            throw new RetenxException(HttpStatus.BAD_REQUEST, "E-mail already verified.");
        }
        UserResource userResource = toUserResource(user);
        userResource.sendVerifyEmail();
    }

    public void sendUpdatePasswordEmail(String email) {
        UserRepresentation user = findByEmail(email);
        UserResource userResource = toUserResource(user);
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public UserResource toUserResource(UserRepresentation userRepresentation) {
        return realmResource.users().get(userRepresentation.getId());
    }
}
