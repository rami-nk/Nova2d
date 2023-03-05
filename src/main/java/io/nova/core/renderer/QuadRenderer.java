package io.nova.core.renderer;

import io.nova.core.renderer.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * An interface representing a quad renderer that can draw textured quads.
 */
public interface QuadRenderer extends DefaultQuadRenderer, RotatedQuadRenderer {
    /**
     * Draws a textured quad at the given position with the given size, texture, tiling factor, and tint color.
     *
     * @param position     the position of the quad
     * @param size         the size of the quad
     * @param texture      the texture to use for the quad
     * @param tilingFactor the tiling factor to apply to the texture
     * @param tintColor    the tint color to apply to the quad
     */
    void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor, Vector4f tintColor);

    /**
     * Draws a rotated textured quad at the given position with the given size, rotation, texture, tiling factor, and tint color.
     *
     * @param position     the position of the quad
     * @param size         the size of the quad
     * @param rotation     the rotation of the quad, in radians
     * @param texture      the texture to use for the quad
     * @param tilingFactor the tiling factor to apply to the texture
     * @param tintColor    the tint color to apply to the quad
     */
    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor, Vector4f tintColor);

    /**
     * Draws a rotated textured quad at the given position with the given size, rotation, texture, and tint color, using a tiling factor of 1.0.
     *
     * @param position  the position of the quad
     * @param size      the size of the quad
     * @param rotation  the rotation of the quad, in radians
     * @param texture   the texture to use for the quad
     * @param tintColor the tint color to apply to the quad
     */
    default void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, Vector4f tintColor) {
        drawRotatedQuad(position, size, rotation, texture, 1.0f, tintColor);
    }

    /**
     * Draws a rotated textured quad at the given position with the given size, rotation, texture, tiling factor, and tint color.
     *
     * @param position     the position of the quad
     * @param size         the size of the quad
     * @param rotation     the rotation of the quad, in radians
     * @param texture      the texture to use for the quad
     * @param tilingFactor the tiling factor to apply to the texture
     * @param tintColor    the tint color to apply to the quad
     */
    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture, float tilingFactor, Vector4f tintColor) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, texture, tilingFactor, tintColor);
    }

    /**
     * Draws a rotated textured quad at the given position with the given size, rotation, texture, and tint color, using a tiling factor of 1.0.
     *
     * @param position  the position of the quad
     * @param size      the size of the quad
     * @param rotation  the rotation of the quad, in radians
     * @param texture   the texture to use for the quad
     * @param tintColor the tint color to apply
     **/
    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture, Vector4f tintColor) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, texture, tintColor);
    }

    /**
     * Draws a textured quad at the given position and size, with the specified tint color.
     *
     * @param position  the position of the quad
     * @param size      the size of the quad
     * @param texture   the texture to apply to the quad
     * @param tintColor the tint color to apply to the quad
     */
    default void drawQuad(Vector3f position, Vector2f size, Texture texture, Vector4f tintColor) {
        drawQuad(position, size, texture, 1.0f, tintColor);
    }

    /**
     * Draws a textured quad at the given position and size, with the specified tint color and tiling factor.
     *
     * @param position     the position of the quad
     * @param size         the size of the quad
     * @param texture      the texture to apply to the quad
     * @param tilingFactor the tiling factor to apply to the texture
     * @param tintColor    the tint color to apply to the quad
     */
    default void drawQuad(Vector2f position, Vector2f size, Texture texture, float tilingFactor, Vector4f tintColor) {
        drawQuad(new Vector3f(position, 0.0f), size, texture, tilingFactor, tintColor);
    }

    /**
     * Draws a textured quad at the given position and size, with the specified tint color and a tiling factor of 1.
     *
     * @param position  the position of the quad
     * @param size      the size of the quad
     * @param texture   the texture to apply to the quad
     * @param tintColor the tint color to apply to the quad
     */
    default void drawQuad(Vector2f position, Vector2f size, Texture texture, Vector4f tintColor) {
        drawQuad(new Vector3f(position, 0.0f), size, texture, tintColor);
    }
}