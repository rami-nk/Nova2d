package io.nova.core;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private final Matrix4f viewMatrix;
    private final Matrix4f modelMatrix;
    private final Vector2f focusPosition;
    private final float zoom;

    /**
     * Creates a camera with a zoom of 1 and a focusPosition of (0 | 0).
     */
    public Camera() {
        this.zoom = 1.0f;
        this.focusPosition = new Vector2f(0.0f, 0.0f);

        this.viewMatrix = new Matrix4f();
        this.modelMatrix = new Matrix4f();
    }

    public Camera(Vector2f focusPosition) {
        this.zoom = 1.0f;
        this.focusPosition = focusPosition;

        this.viewMatrix = new Matrix4f();
        this.modelMatrix = new Matrix4f();
    }

    public Camera(Vector2f focusPosition, float zoom) {
        this.zoom = zoom;
        this.focusPosition = focusPosition;

        this.viewMatrix = new Matrix4f();
        this.modelMatrix = new Matrix4f();
    }

    public void move(Vector2f positionIncrement) {
        focusPosition.add(positionIncrement);
    }

    public Matrix4f getProjectionMatrix() {
        float left = focusPosition.x - Nova2dWindow.getWidth() / 2.0f;
        float right = focusPosition.x + Nova2dWindow.getWidth() / 2.0f;
        float top = focusPosition.y + Nova2dWindow.getHeight() / 2.0f;
        float bottom = focusPosition.y - Nova2dWindow.getHeight() / 2.0f;

        var projectionMatrix = new Matrix4f().ortho( left, right, bottom, top, 0.1f, 100.0f);
        var zoomMatrix = new Matrix4f().scale(zoom);
        return projectionMatrix.mul(zoomMatrix);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(focusPosition.x, focusPosition.y, 0.0f),
                cameraFront.add(focusPosition.x, focusPosition.y, 0.0f),
                cameraUp);
        return viewMatrix;
    }

    public Vector2f getFocusPosition() {
        return focusPosition;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }
}