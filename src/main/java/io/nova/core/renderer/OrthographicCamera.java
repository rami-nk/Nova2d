package io.nova.core.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class OrthographicCamera {

    protected Matrix4f projectionMatrix;
    protected Matrix4f viewMatrix;
    protected Matrix4f viewProjectionMatrix;
    protected Vector3f position;
    protected float rotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        projectionMatrix = new Matrix4f().ortho( left, right, bottom, top, -1.0f, 1.0f);
        viewMatrix = new Matrix4f();
        viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
    }

    private void recalculateViewMatrix() {
        var transform = new Matrix4f().translate(position, new Matrix4f())
                .mul(new Matrix4f().rotate(rotation, new Vector3f(0, 0, 1), new Matrix4f()));
        viewMatrix = transform.invert();
        viewProjectionMatrix = new Matrix4f(projectionMatrix).mul(viewMatrix);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        recalculateViewMatrix();
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        recalculateViewMatrix();
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }

}