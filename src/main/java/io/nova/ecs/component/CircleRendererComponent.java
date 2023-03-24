package io.nova.ecs.component;

import org.joml.Vector4f;

public class CircleRendererComponent extends Component {
    private float[] color;
    private float thickness;
    private float fade;

    public CircleRendererComponent() {
        this.color = new float[]{1, 1, 1, 1};
        this.thickness = 1.0f;
        this.fade = 0.005f;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public Vector4f getColorAsVec() {
        return new Vector4f(color);
    }

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public float getFade() {
        return fade;
    }

    public void setFade(float fade) {
        this.fade = fade;
    }
}