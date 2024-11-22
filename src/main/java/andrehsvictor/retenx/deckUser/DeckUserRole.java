package andrehsvictor.retenx.deckUser;

public enum DeckUserRole {
    AUTHOR, USER;

    public static DeckUserRole fromString(String role) {
        if (role == null) {
            return USER;
        }
        return DeckUserRole.valueOf(role.toUpperCase());
    }
}
