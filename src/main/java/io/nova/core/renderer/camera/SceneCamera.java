package io.nova.core.renderer.camera;

import org.joml.Matrix4f;

public class SceneCamera extends Camera {

    private float aspectRatio;
    private float nearPlane, farPlane;
    private float orthographicSize;

    public SceneCamera() {
        setOrthographic(10.0f, -1.0f, 1.0f);
        aspectRatio = 16.0f / 9.0f;

        updateProjection();
    }

    public void setOrthographic(float size, float nearPlane, float farPlane) {
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        this.orthographicSize = size;
        updateProjection();
    }

    public void setViewport(int width, int height) {
        this.aspectRatio = (float) width / (float) height;
        updateProjection();
    }

    private void updateProjection() {
        projection = new Matrix4f().ortho(
                -orthographicSize * aspectRatio * 0.5f,
                orthographicSize * aspectRatio * 0.5f,
                -orthographicSize * 0.5f,
                orthographicSize * 0.5f,
                nearPlane, farPlane);
    }

    public float getOrthographicSize() {
        return orthographicSize;
    }

    public void setOrthographicSize(float size) {
        this.orthographicSize = size;
        updateProjection();
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public float getFarPlane() {
        return farPlane;
    }
}