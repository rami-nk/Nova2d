package io.nova.core.renderer.texture;

import org.joml.Vector2f;

public class SubTexture {
    private final Texture texture;
    private final Vector2f[] textureCoordinates;

    public SubTexture(Texture texture, float minX, float minY, float maxX, float maxY) {
        this.texture = texture;
        textureCoordinates = new Vector2f[]{
                new Vector2f(minX, minY),
                new Vector2f(maxX, minY),
                new Vector2f(maxX, maxY),
                new Vector2f(minX, maxY)
        };
    }

    public SubTexture(Texture texture, Vector2f coords, Vector2f spriteSize) {
        this(texture,
                (coords.x * spriteSize.x) / texture.getWidth(),
                (coords.y * spriteSize.y) / texture.getHeight(),
                ((coords.x + 1) * spriteSize.x) / texture.getWidth(),
                ((coords.y + 1) * spriteSize.y) / texture.getHeight());
    }

    public Vector2f[] getTextureCoordinates() {
        return textureCoordinates;
    }

    public Texture getTexture() {
        return texture;
    }
}