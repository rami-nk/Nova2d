package io.nova.core.renderer.camera;

import org.joml.Matrix4f;

public class Camera {
    private Matrix4f projection;

    public Camera() {
        this(new Matrix4f());
    }

    public Camera(Matrix4f projection) {
        this.projection = projection;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }
}