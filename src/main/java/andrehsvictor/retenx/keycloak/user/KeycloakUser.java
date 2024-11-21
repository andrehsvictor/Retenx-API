package andrehsvictor.retenx.keycloak.user;

import java.io.Serializable;
import java.util.List;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private UserResource userResource;

    public String getFirstName() {
        return userResource.toRepresentation().getFirstName();
    }

    public String getLastName() {
        return userResource.toRepresentation().getLastName();
    }

    public String getEmail() {
        return userResource.toRepresentation().getEmail();
    }

    public String getUsername() {
        return userResource.toRepresentation().getUsername();
    }

    public String getId() {
        return userResource.toRepresentation().getId();
    }

    public List<String> getRealmRoles() {
        return userResource.toRepresentation().getRealmRoles();
    }

    public List<String> getRequiredActions() {
        return userResource.toRepresentation().getRequiredActions();
    }

    public boolean isEnabled() {
        return userResource.toRepresentation().isEnabled();
    }

    public boolean isEmailVerified() {
        return userResource.toRepresentation().isEmailVerified();
    }

    public String getAvatarUrl() {
        return userResource.toRepresentation().firstAttribute("avatarUrl");
    }

    public String getAttribute(String key) {
        return userResource.toRepresentation().firstAttribute(key);
    }

    public void setAttribute(String key, String value) {
        userResource.toRepresentation().singleAttribute(key, value);
    }

    public void setFirstName(String firstName) {
        userResource.toRepresentation().setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        userResource.toRepresentation().setLastName(lastName);
    }

    public void setEmail(String email) {
        userResource.toRepresentation().setEmail(email);
    }

    public void setUsername(String username) {
        userResource.toRepresentation().setUsername(username);
    }

    public void setRealmRoles(List<String> realmRoles) {
        userResource.toRepresentation().setRealmRoles(realmRoles);
    }

    public void setRequiredActions(List<String> requiredActions) {
        userResource.toRepresentation().setRequiredActions(requiredActions);
    }

    public void setEnabled(boolean enabled) {
        userResource.toRepresentation().setEnabled(enabled);
    }

    public void setEmailVerified(boolean emailVerified) {
        userResource.toRepresentation().setEmailVerified(emailVerified);
    }

    public void setPassword(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        userResource.toRepresentation().setCredentials(List.of(credential));
    }

    public void setAvatarUrl(String avatarUrl) {
        userResource.toRepresentation().singleAttribute("avatarUrl", avatarUrl);
    }

    public void update() {
        userResource.update(userResource.toRepresentation());
    }

    public void delete() {
        userResource.remove();
    }

    public void sendVerifyEmail() {
        userResource.sendVerifyEmail();
    }

    public void executeActionsEmail(List<String> actions) {
        userResource.executeActionsEmail(actions);
    }

    public UserRepresentation toRepresentation() {
        return userResource.toRepresentation();
    }

}
