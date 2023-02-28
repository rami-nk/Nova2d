package io.nova.core.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public interface Shader {
    void bind();

    void unbind();

    void setUniformMat4f(String name, Matrix4f mat4f);

    void setUniformVec2f(String name, Vector2f vec2f);

    void setUniformInt(String name, int value);

    void setUniformTexture(String name, int slot);

    void setUniformIntArray(String name, int[] values);

    void setUniformTextureArray(String name, int[] slots);
}
