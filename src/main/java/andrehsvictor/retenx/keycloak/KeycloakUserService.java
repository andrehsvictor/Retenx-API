package andrehsvictor.retenx.keycloak;

import java.util.List;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    @CachePut(value = "userRepresentation", key = "#result.id")
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
                throw new RetenxException(HttpStatus.CONFLICT, "Username or e-mail already in use.");
            default:
                throw new RetenxException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while creating user.");
        }
        String userId = CreatedResponseUtil.getCreatedId(response);

        return findById(userId);
    }

    @Transactional
    @CachePut(value = "userRepresentation", key = "#id")
    public UserRepresentation update(String id, UserRepresentation userRepresentation) {
        UserRepresentation currentUser = findById(id);
        if (!currentUser.getEmail().equals(userRepresentation.getEmail())) {
            currentUser.setEmailVerified(false);
        }
        UserResource userResource = toUserResource(currentUser);
        userResource.update(userRepresentation);
        return findById(id);
    }

    @Transactional
    @CacheEvict(value = { "userRepresentation", "userResource" }, key = "#id")
    public void deleteById(String id) {
        UserRepresentation userRepresentation = findById(id);
        UserResource userResource = toUserResource(userRepresentation);
        userResource.remove();
    }

    @Cacheable(value = "userRepresentation", key = "#id")
    public UserRepresentation findById(String id) {
        String search = String.format(SEARCH_BY_ID, id);
        List<UserRepresentation> users = realmResource.users().search(search, 0, 1);
        if (users.isEmpty()) {
            throw new RetenxException(HttpStatus.NOT_FOUND, "User not found with external ID: " + id + ".");
        }
        return users.getFirst();
    }

    @Cacheable(value = "userRepresentation", key = "#id")
    public boolean existsById(String id) {
        String search = String.format(SEARCH_BY_ID, id);
        Integer count = realmResource.users().count(search);
        return count > 0;
    }

    @Cacheable(value = "userRepresentation", key = "#email")
    public boolean isEmailVerified(String email) {
        UserRepresentation user = findByEmail(email);
        return user.isEmailVerified();
    }

    @Cacheable(value = "userRepresentation", key = "#email")
    public UserRepresentation findByEmail(String email) {
        List<UserRepresentation> users = realmResource.users().searchByEmail(email, true);
        if (users.isEmpty()) {
            throw new RetenxException(HttpStatus.NOT_FOUND, "User not found with e-mail: " + email + ".");
        }
        return users.getFirst();
    }

    @CacheEvict(value = { "userRepresentation", "userResource" }, key = "findByEmail(#email).id")
    public void sendVerifyEmail(String email) {
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

    @Cacheable(value = "userResource", key = "#userRepresentation.id")
    public UserResource toUserResource(UserRepresentation userRepresentation) {
        return realmResource.users().get(userRepresentation.getId());
    }
}
