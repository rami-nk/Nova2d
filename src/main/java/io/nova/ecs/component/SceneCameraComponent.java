package io.nova.ecs.component;

import io.nova.core.renderer.camera.SceneCamera;

public class SceneCameraComponent extends Component {

    private SceneCamera camera;
    private boolean primary;
    private boolean fixedAspectRatio;

    public SceneCameraComponent() {
        this.camera = new SceneCamera();
    }

    public boolean isFixedAspectRatio() {
        return fixedAspectRatio;
    }

    public void setFixedAspectRatio(boolean fixedAspectRatio) {
        this.fixedAspectRatio = fixedAspectRatio;
    }

    public SceneCamera getCamera() {
        return camera;
    }

    public void setCamera(SceneCamera camera) {
        this.camera = camera;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}