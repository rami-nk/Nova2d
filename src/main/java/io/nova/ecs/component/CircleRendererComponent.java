package io.nova.ecs.component;

public class CircleRendererComponent extends Component {
    private float[] color;
    private float radius;
    private float thickness;
    private float fade;

    public CircleRendererComponent() {
        this.color = new float[]{1, 1, 1, 1};
        this.radius = 0.5f;
        this.thickness = 1.0f;
        this.fade = 0.005f;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] color) {
        this.color = color;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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