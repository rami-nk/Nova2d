package io.nova.core.renderer;

import io.nova.core.renderer.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * The RotatedQuadRenderer interface defines methods for rendering rotated quads with colors or textures.
 */
public interface RotatedQuadRenderer {
    /**
     * Renders a rotated quad with a solid color.
     *
     * @param position the position of the center of the quad
     * @param size     the size of the quad
     * @param rotation the rotation of the quad in radians
     * @param color    the color of the quad
     */
    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color);

    /**
     * Renders a rotated quad with a texture.
     *
     * @param position     the position of the center of the quad
     * @param size         the size of the quad
     * @param rotation     the rotation of the quad in radians
     * @param texture      the texture of the quad
     * @param tilingFactor the tiling factor of the texture
     */
    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor);

    /**
     * Renders a rotated quad with a solid color.
     *
     * @param position the position of the center of the quad
     * @param size     the size of the quad
     * @param rotation the rotation of the quad in radians
     * @param color    the color of the quad
     */
    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Vector4f color) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, color);
    }

    /**
     * Renders a rotated quad with a texture.
     *
     * @param position the position of the center of the quad
     * @param size     the size of the quad
     * @param rotation the rotation of the quad in radians
     * @param texture  the texture of the quad
     */
    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, texture);
    }

    /**
     * Renders a rotated quad with a texture.
     *
     * @param position the position of the center of the quad
     * @param size     the size of the quad
     * @param rotation the rotation of the quad in radians
     * @param texture  the texture of the quad
     */
    default void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, texture, 1.0f);
    }

    /**
     * Renders a rotated quad with a texture.
     *
     * @param position the position of the center of the quad
     * @param size     the size of the quad
     * @param rotation the rotation of the quad in radians
     * @param texture  the texture of the quad
     */
    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, texture, tilingFactor);
    }
}