package io.nova.ecs.component;

import org.joml.Vector4f;

public class SpriteRenderComponent extends Component {

    private Vector4f color;
    private float[] colorArray;

    public SpriteRenderComponent() {
        this(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
    }

    public SpriteRenderComponent(Vector4f color) {
        this.color = color;
        this.colorArray = new float[]{color.x, color.y, color.z, color.w};
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void setColor(float[] colorArray) {
        this.colorArray = colorArray;
        color.set(colorArray[0], colorArray[1], colorArray[2], colorArray[3]);
    }

    public float[] getColorArray() {
        return colorArray;
    }
}