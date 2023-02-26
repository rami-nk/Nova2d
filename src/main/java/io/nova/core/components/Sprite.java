package io.nova.core.components;

import io.nova.core.Texture2d;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Sprite extends Component {

    private final Vector4f color;
    private Vector2f[] textureCoordinates;
    private int textureId;

    public Sprite(Vector4f color) {
        this.color = color;
        this.textureCoordinates = defaultTextureCoordinates();
        textureId = Texture2d.RESERVED_TEXTURE_SLOT_ID;
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

    public Sprite(int textureId, Vector2f[] textureCoordinates) {
        this.textureId = textureId;
        this.textureCoordinates = textureCoordinates;
        this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Vector2f[] getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(Vector2f[] textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
        setDirty();
    }

    public Vector4f getColor() {
        return color;
    }

    public int getTextureId() {
        return textureId;
    }

    private Vector2f[] defaultTextureCoordinates() {
        return new Vector2f[]{
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1),
        };
    }

    public void setSprite(Sprite sprite) {
        if (!this.equals(sprite)) {
            this.textureId = sprite.getTextureId();
            this.textureCoordinates = sprite.getTextureCoordinates();
            this.position = sprite.getPosition();
            this.size = sprite.getSize();
            setDirty();
        }
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
        setDirty();
    }
}