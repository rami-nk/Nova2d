package io.nova.core.renderer;

import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public interface TintedQuadRenderer {
    void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor, Vector4f tintColor);

    default void drawQuad(Vector3f position, Vector2f size, Texture texture, Vector4f tintColor) {
        drawQuad(position, size, texture, 1.0f, tintColor);
    }

    default void drawQuad(Vector2f position, Vector2f size, Texture texture, float tilingFactor, Vector4f tintColor) {
        drawQuad(new Vector3f(position, 0.0f), size, texture, tilingFactor, tintColor);
    }

    default void drawQuad(Vector2f position, Vector2f size, Texture texture, Vector4f tintColor) {
        drawQuad(new Vector3f(position, 0.0f), size, texture, tintColor);
    }

    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor, Vector4f tintColor);

    default void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, Vector4f tintColor) {
        drawRotatedQuad(position, size, rotation, texture, 1.0f, tintColor);
    }

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture, float tilingFactor, Vector4f tintColor) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, texture, tilingFactor, tintColor);
    }

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, Texture texture, Vector4f tintColor) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, texture, tintColor);
    }

    /*
     * ###########################################################################
     * ##########################  SUB-TEXTURES ##################################
     * ###########################################################################
     */

    void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, float tilingFactor, Vector4f tintColor);

    void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor, Vector4f tintColor);

    default void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, SubTexture subTexture, Vector4f tintColor) {
        drawRotatedQuad(position, size, rotation, subTexture, 1.0f, tintColor);
    }

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, subTexture, tilingFactor, tintColor);
    }

    default void drawRotatedQuad(Vector2f position, Vector2f size, float rotation, SubTexture subTexture, Vector4f tintColor) {
        drawRotatedQuad(new Vector3f(position, 0.0f), size, rotation, subTexture, tintColor);
    }

    default void drawQuad(Vector3f position, Vector2f size, SubTexture subTexture, Vector4f tintColor) {
        drawQuad(position, size, subTexture, 1.0f, tintColor);
    }

    default void drawQuad(Vector2f position, Vector2f size, SubTexture subTexture, float tilingFactor, Vector4f tintColor) {
        drawQuad(new Vector3f(position, 0.0f), size, subTexture, tilingFactor, tintColor);
    }

    default void drawQuad(Vector2f position, Vector2f size, SubTexture subTexture, Vector4f tintColor) {
        drawQuad(new Vector3f(position, 0.0f), size, subTexture, tintColor);
    }
}