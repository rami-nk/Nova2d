package io.nova.ecs.component;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TransformComponent extends Component {
    private Matrix4f transform;
    private float[] position;
    private float[] scale;

    public TransformComponent() {
        this(new Matrix4f());
    }

    public TransformComponent(Matrix4f transform) {
        this.transform = transform;
        var translation = transform.getTranslation(new Vector3f());
        this.position = new float[]{translation.x, translation.y, translation.z};
        var _scale = transform.getScale(new Vector3f());
        this.scale = new float[]{_scale.x, _scale.y, _scale.z};
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
        transform.setTranslation(position[0], position[1], position[2]);
    }

    public float[] getScale() {
        return scale;
    }

    public void setScale(float[] scale) {
        this.scale = scale;
        transform.scale(scale[0], scale[1], scale[2]);
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