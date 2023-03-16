package io.nova.ecs.component;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TransformComponent extends Component {
    private float[] translation;
    private float[] rotation;
    private float[] scale;

    public TransformComponent() {
        translation = new float[]{0, 0, 0};
        rotation = new float[]{0, 0, 0};
        scale = new float[]{1, 1, 1};
    }

    public float[] getRotation() {
        return rotation;
    }

    public void setRotation(float[] rotation) {
        this.rotation = rotation;
    }

    public float[] getTranslation() {
        return translation;
    }

    public void setTranslation(float[] translation) {
        this.translation = translation;
    }

    public float[] getScale() {
        return scale;
    }

    public void setScale(float[] scale) {
        this.scale = scale;
    }

    public Matrix4f getTransform() {
        var rot = new Matrix4f()
                .rotate(this.rotation[0], new Vector3f(1, 0, 0))
                .rotate(this.rotation[1], new Vector3f(0, 1, 0))
                .rotate(this.rotation[2], new Vector3f(0, 0, 1));
        return new Matrix4f()
                .translate(new Vector3f(translation[0], translation[1], translation[2]))
                .mul(rot)
                .scale(scale[0], scale[1], scale[2]);
    }

    public void translate(float x, float y, float z) {
        translation[0] += x;
        translation[1] += y;
        translation[2] += z;
    }

    public void translate(float x, float y) {
        translate(x, y, 0);
    }
}