package io.nova.core.renderer.camera;

public enum ProjectionType {
    PERSPECTIVE("perspective"),
    ORTHOGRAPHIC("orthographic");

    private final String displayName;

    ProjectionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}