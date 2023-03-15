package io.nova.ecs.component;

public class SpriteRenderComponent extends Component {

    private float[] color;

    public SpriteRenderComponent() {
        this.color = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] colorArray) {
        this.color = colorArray;
    }
}