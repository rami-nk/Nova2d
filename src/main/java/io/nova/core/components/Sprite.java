package io.nova.core.components;

import org.joml.Vector4f;

public class Sprite extends Component {

    private Vector4f color;

    public Sprite(Vector4f color) {
        this.color = color;
    }

    @Override
    public void update(double deltaTime) {

    }

    public Vector4f getColor() {
        return color;
    }
}
