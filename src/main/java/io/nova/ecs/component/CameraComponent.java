package io.nova.ecs.component;

import io.nova.core.renderer.camera.Camera;
import org.joml.Matrix4f;

public class CameraComponent extends Component {

    private Camera camera;
    private boolean primary;

    public CameraComponent() {
        this(new Matrix4f().ortho(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f));
    }

    public CameraComponent(Matrix4f projection) {
        this.camera = new Camera(projection);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}