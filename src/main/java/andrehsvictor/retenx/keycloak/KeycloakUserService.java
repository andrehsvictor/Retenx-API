package andrehsvictor.retenx.keycloak;

import java.util.List;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private final RealmResource realmResource;

    public UserRepresentation findById(String id) {
        String search = "id:" + id;
        List<UserRepresentation> users = realmResource.users().search(search, 0, 1);
        if (users.isEmpty()) {
            throw new RetenxException(HttpStatus.NOT_FOUND, "User not found in Keycloak with ID: " + id + ".");
        }
        return users.getFirst();
    }

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
