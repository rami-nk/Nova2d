package io.nova.core.renderer.texture;

import org.joml.Vector2f;

public class SubTexture {
    private Texture texture;
    private float[] textureCoordinates;
    private float[] leftTop, rightBottom;

    public SubTexture() {
        // For deserialization purposes
    }

    public SubTexture(Texture texture, float minX, float minY, float maxX, float maxY) {
        this.texture = texture;
        textureCoordinates = new float[]{
                minX, minY,
                maxX, minY,
                maxX, maxY,
                minX, maxY
        };
        this.leftTop = new float[]{minX, maxY};
        this.rightBottom = new float[]{maxX, minY};
    }

    public SubTexture(Texture texture, Vector2f coords, Vector2f spriteSize) {
        this(texture,
                (coords.x * spriteSize.x) / texture.getWidth(),
                (coords.y * spriteSize.y) / texture.getHeight(),
                ((coords.x + 1) * spriteSize.x) / texture.getWidth(),
                ((coords.y + 1) * spriteSize.y) / texture.getHeight());
    }

    public float[] getTextureCoordinates() {
        return textureCoordinates;
    }

    public float[] getLeftTop() {
        return leftTop;
    }

    public float[] getRightBottom() {
        return rightBottom;
    }

    public Texture getTexture() {
        return texture;
    }
}