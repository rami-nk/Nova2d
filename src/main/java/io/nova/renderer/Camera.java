package io.nova.renderer;

import io.nova.core.application.Application;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera {
    private Vector2f focusPosition;
    private float zoom;

    private int width;
    private int height;

    /**
     * Creates a camera with a zoom of 1 and a focusPosition of (0 | 0).
     */
    public Camera() {
        this.zoom = 1.0f;
        this.focusPosition = new Vector2f(0.0f, 0.0f);
        this.width = Application.getWindow().getWidth();
        this.height = Application.getWindow().getHeight();
    }

    public Camera(Vector2f focusPosition) {
        this();
        this.focusPosition = focusPosition;
    }

    public Camera(Vector2f focusPosition, float zoom) {
        this();
        this.zoom = zoom;
        this.focusPosition = focusPosition;
    }

    public Camera(int width, int height) {
        this();
        this.width = width;
        this.height = height;
    }

    public void move(Vector2f positionIncrement) {
        focusPosition.add(positionIncrement);
    }

    public Matrix4f getProjectionMatrix() {
        float left = focusPosition.x - width / 2.0f;
        float right = focusPosition.x + width / 2.0f;
        float top = focusPosition.y + height / 2.0f;
        float bottom = focusPosition.y - height / 2.0f;

        var projectionMatrix = new Matrix4f().ortho( left, right, bottom, top, 0.1f, 100.0f);
        var zoomMatrix = new Matrix4f().scale(zoom);
        return projectionMatrix.mul(zoomMatrix);
    }

    public void zoom(float factor) {
        zoom *= factor;
    }


    public Vector2f getFocusPosition() {
        return focusPosition;
    }
}