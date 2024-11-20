package andrehsvictor.retenx.keycloak.user;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class KeycloakUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> realmRoles;
    private List<String> requiredActions;
    private boolean enabled;
    private boolean emailVerified;
    private String password;
    private String avatarUrl;

}
