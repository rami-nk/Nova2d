package io.nova.ecs.component;

import org.joml.Vector4f;

public class SpriteRenderComponent extends Component {

    private Vector4f color;

    public SpriteRenderComponent() {
        this(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
    }

    public SpriteRenderComponent(Vector4f color) {
        this.color = color;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }
}