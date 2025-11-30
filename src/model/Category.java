package model;

/**
 * Represents expense categories with icons and display names.
 *
 * Type-safe enumeration ensuring valid categories across the application.
 * Each category includes an emoji icon and Polish display name for UI.
 *
 * @author Konrad Wojdyna
 * @version 0.3.0
 */

public enum Category {
    FOOD("🍔", "Jedzenie"),
    TRANSPORT("🚗", "Transport"),
    ENTERTAINMENT("🎬", "Rozrywka"),
    HOUSING("🏠", "Mieszkanie"),
    HEALTHCARE("💊", "Zdrowie"),
    OTHER("📦", "Inne");

    private final String icon;
    private final String displayName;

    /**
     * Private constructor for enum constants.
     *
     * @param icon emoji icon for visual representation
     * @param displayName Polish display name
     */
    Category(String icon, String displayName){
        this.icon = icon;
        this.displayName = displayName;
    }

    public String getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns formatted label with icon and name.
     *
     * @return formatted string like "🍔 Jedzenie"
     */
    public String getLabel(){
        return icon + " " + displayName;
    }
}
