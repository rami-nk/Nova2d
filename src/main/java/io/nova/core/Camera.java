package io.nova.core;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private final Vector2f position;
    private final int height;
    private final int width;

    public Camera(int width, int height, Vector2f position) {
        this.height = height;
        this.width = width;
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    private void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, width, 0.0f, height, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 0.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);
        return viewMatrix;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}