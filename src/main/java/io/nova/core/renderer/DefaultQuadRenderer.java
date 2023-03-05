package io.nova.core.renderer;

import io.nova.core.renderer.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * The DefaultQuadRenderer interface defines methods for rendering textured and colored quads with default settings.
 */
public interface DefaultQuadRenderer {
    /**
     * Renders a quad with the given position, size, and color.
     *
     * @param position the position of the quad
     * @param size     the size of the quad
     * @param color    the color of the quad
     */
    void drawQuad(Vector3f position, Vector2f size, Vector4f color);

    /**
     * Renders a textured quad with the given position, size, texture, and tiling factor.
     *
     * @param position     the position of the quad
     * @param size         the size of the quad
     * @param texture      the texture of the quad
     * @param tilingFactor the tiling factor of the texture
     */
    void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor);

    /**
     * Renders a colored quad with the given position, size, and color.
     *
     * @param position the position of the quad
     * @param size     the size of the quad
     * @param color    the color of the quad
     */
    default void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
        drawQuad(new Vector3f(position, 0.0f), size, color);
    }

    /**
     * Renders a textured quad with the given position, size, and texture.
     *
     * @param position the position of the quad
     * @param size     the size of the quad
     * @param texture  the texture of the quad
     */
    default void drawQuad(Vector2f position, Vector2f size, Texture texture) {
        drawQuad(new Vector3f(position, 0.0f), size, texture);
    }

    /**
     * Renders a textured quad with the given position, size, texture, and default tiling factor of 1.0f.
     *
     * @param position the position of the quad
     * @param size     the size of the quad
     * @param texture  the texture of the quad
     */
    default void drawQuad(Vector3f position, Vector2f size, Texture texture) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, texture, 1.0f);
    }

    /**
     * Renders a textured quad with the given position, size, texture, and tiling factor.
     *
     * @param position     the position of the quad
     * @param size         the size of the quad
     * @param texture      the texture of the quad
     * @param tilingFactor the tiling factor of the texture
     */
    default void drawQuad(Vector2f position, Vector2f size, Texture texture, float tilingFactor) {
        drawQuad(new Vector3f(position.x, position.y, 0.0f), size, texture, tilingFactor);
    }
}