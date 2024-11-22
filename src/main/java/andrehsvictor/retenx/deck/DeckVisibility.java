package andrehsvictor.retenx.deck;

public enum DeckVisibility {
    PUBLIC, PRIVATE;

    public static DeckVisibility fromString(String visibility) {
        if (visibility == null) {
            return PRIVATE;
        }
        return DeckVisibility.valueOf(visibility.toUpperCase());
    }
}
