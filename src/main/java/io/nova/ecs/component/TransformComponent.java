package io.nova.ecs.component;

import org.joml.Matrix4f;

public class TransformComponent extends Component {
    private Matrix4f transform;

    public TransformComponent() {
        this(new Matrix4f());
    }

    public TransformComponent(Matrix4f transform) {
        this.transform = transform;
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }

    public void translate(float x, float y, float z) {
        transform.translate(x, y, z);
    }

    public void translate(float x, float y) {
        translate(x, y, 0);
    }
}