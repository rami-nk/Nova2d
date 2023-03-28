package panels.contentbrowser;

enum SpriteMode {
    SINGLE("Single"),
    MULTIPLE("Multiple");

    private final String displayName;

    SpriteMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}