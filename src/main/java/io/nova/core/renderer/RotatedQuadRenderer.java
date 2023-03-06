package io.nova.core.renderer;

import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface RotatedQuadRenderer {

    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color);

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Vector4f color) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, color);
    }

    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor);

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, texture);
    }

    default void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, texture, 1.0f);
    }

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, texture, tilingFactor);
    }

    /*
     * ###########################################################################
     * ##########################  SUB-TEXTURES ##################################
     * ###########################################################################
     */

    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor);

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, SubTexture subTexture) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, subTexture);
    }

    default void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, subTexture, 1.0f);
    }

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor) {
        drawRotatedQuad(new Vector3f(position.x, position.y, 0.0f), size, rotation, subTexture, tilingFactor);
    }
}