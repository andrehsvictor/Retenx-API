package andrehsvictor.retenx.keycloak.user;

import java.io.Serializable;
import java.util.List;

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

    private UserRepresentation userRepresentation = new UserRepresentation();

    public String getFirstName() {
        return userRepresentation.getFirstName();
    }

    public String getLastName() {
        return userRepresentation.getLastName();
    }

    public String getEmail() {
        return userRepresentation.getEmail();
    }

    public String getUsername() {
        return userRepresentation.getUsername();
    }

    public String getId() {
        return userRepresentation.getId();
    }

    public List<String> getRealmRoles() {
        return userRepresentation.getRealmRoles();
    }

    public List<String> getRequiredActions() {
        return userRepresentation.getRequiredActions();
    }

    public boolean isEnabled() {
        return userRepresentation.isEnabled();
    }

    public boolean isEmailVerified() {
        return userRepresentation.isEmailVerified();
    }

    public String getAvatarUrl() {
        return userRepresentation.firstAttribute("avatarUrl");
    }

    public String getAttribute(String key) {
        return userRepresentation.firstAttribute(key);
    }

    public void setAttribute(String key, String value) {
        userRepresentation.singleAttribute(key, value);
    }

    public void setFirstName(String firstName) {
        userRepresentation.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
        userRepresentation.setLastName(lastName);
    }

    public void setEmail(String email) {
        userRepresentation.setEmail(email);
    }

    public void setUsername(String username) {
        userRepresentation.setUsername(username);
    }

    public void setRealmRoles(List<String> realmRoles) {
        userRepresentation.setRealmRoles(realmRoles);
    }

    public void setRequiredActions(List<String> requiredActions) {
        userRepresentation.setRequiredActions(requiredActions);
    }

    public void setEnabled(boolean enabled) {
        userRepresentation.setEnabled(enabled);
    }

    public void setEmailVerified(boolean emailVerified) {
        userRepresentation.setEmailVerified(emailVerified);
    }

    public void setPassword(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        userRepresentation.setCredentials(List.of(credential));
    }

    public void setAvatarUrl(String avatarUrl) {
        userRepresentation.singleAttribute("avatarUrl", avatarUrl);
    }

}
