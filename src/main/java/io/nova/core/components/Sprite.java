package io.nova.core.components;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class Sprite extends Component {

    private final Vector4f color;
    private Vector2f[] textureCoordinates;
    private int textureId;

    public Sprite(Vector4f color) {
        this.color = color;
        this.textureCoordinates = defaultTextureCoordinates();
    }

    public Sprite(Vector4f color, Vector2f[] textureCoordinates) {
        this(color);
        this.textureCoordinates = textureCoordinates;
    }

    public Sprite(Vector4f color, int textureId) {
        this(color);
        this.textureId = textureId;
    }

    public Sprite(int textureId) {
        this(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
        this.textureId = textureId;
    }

    @Override
    public void update(double deltaTime) {

    }

    public Vector2f[] getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(Vector2f[] textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }

    public Vector4f getColor() {
        return color;
    }

    private Vector2f[] defaultTextureCoordinates() {
        return new Vector2f[]{
                new Vector2f(1, 1),
                new Vector2f(0, 1),
                new Vector2f(0, 0),
                new Vector2f(1, 0),
        };
    }
}