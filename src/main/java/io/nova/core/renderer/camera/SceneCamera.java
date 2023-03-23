package io.nova.core.renderer.camera;

import org.joml.Matrix4f;

public class SceneCamera extends Camera {

    private float aspectRatio;
    private float orthographicNearPlane, orthographicFarPlane;
    private float orthographicSize;

    private float fov;
    private float perspectiveNearPlane, perspectiveFarPlane;

    public SceneCamera() {
        this(ProjectionType.ORTHOGRAPHIC);
    }

    public SceneCamera(ProjectionType projectionType) {
        this.projectionType = projectionType;
        this.aspectRatio = 16.0f / 9.0f;

        setDefaultPerspectiveParams();
        setDefaultOrthographicParams();

        switch (projectionType) {
            case PERSPECTIVE -> setPerspective(fov, perspectiveNearPlane, perspectiveFarPlane);
            case ORTHOGRAPHIC -> setOrthographic(orthographicSize, orthographicNearPlane, orthographicFarPlane);
        }
        updateProjection();
    }

    private void setDefaultOrthographicParams() {
        orthographicNearPlane = -1.0f;
        orthographicFarPlane = 1.0f;
        orthographicSize = 10.0f;
    }

    private void setDefaultPerspectiveParams() {
        fov = (float) Math.toRadians(45.0f);
        perspectiveNearPlane = 0.1f;
        perspectiveFarPlane = 1000.0f;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
        updatePerspective();
    }

    public void setProjectionType(ProjectionType projectionType) {
        super.setProjectionType(projectionType);
        updateProjection();
    }

    public float getPerspectiveNearPlane() {
        return perspectiveNearPlane;
    }

    public void setPerspectiveNearPlane(float perspectiveNearPlane) {
        this.perspectiveNearPlane = perspectiveNearPlane;
        updatePerspective();
    }

    public float getPerspectiveFarPlane() {
        return perspectiveFarPlane;
    }

    public void setPerspectiveFarPlane(float perspectiveFarPlane) {
        this.perspectiveFarPlane = perspectiveFarPlane;
        updatePerspective();
    }

    public void setPerspective(float fov, float farPlane, float nearPlane) {
        this.fov = fov;
        this.perspectiveNearPlane = nearPlane;
        this.perspectiveFarPlane = farPlane;
        updatePerspective();
    }

    public void setOrthographic(float size, float nearPlane, float farPlane) {
        this.orthographicNearPlane = nearPlane;
        this.orthographicFarPlane = farPlane;
        this.orthographicSize = size;
        updateOrthographic();
    }

    public void setViewport(int width, int height) {
        if (width != 0 && height != 0) {
            this.aspectRatio = (float) width / (float) height;
        }
        updateProjection();
    }

    private void updateProjection() {
        switch (projectionType) {
            case PERSPECTIVE -> updatePerspective();
            case ORTHOGRAPHIC -> updateOrthographic();
        }
    }

    public void updatePerspective() {
        projection = new Matrix4f().perspective(
                fov,
                aspectRatio,
                perspectiveNearPlane,
                perspectiveFarPlane);
    }

    public void updateOrthographic() {
        projection = new Matrix4f().ortho(
                -orthographicSize * aspectRatio * 0.5f,
                orthographicSize * aspectRatio * 0.5f,
                -orthographicSize * 0.5f,
                orthographicSize * 0.5f,
                orthographicNearPlane, orthographicFarPlane);
    }

    public float getOrthographicSize() {
        return orthographicSize;
    }

    public void setOrthographicSize(float size) {
        this.orthographicSize = size;
        updateOrthographic();
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public float getOrthographicNearPlane() {
        return orthographicNearPlane;
    }

    public void setOrthographicNearPlane(float orthographicNearPlane) {
        this.orthographicNearPlane = orthographicNearPlane;
        updateOrthographic();
    }

    public float getOrthographicFarPlane() {
        return orthographicFarPlane;
    }

    public void setOrthographicFarPlane(float orthographicFarPlane) {
        this.orthographicFarPlane = orthographicFarPlane;
        updateOrthographic();
    }
}