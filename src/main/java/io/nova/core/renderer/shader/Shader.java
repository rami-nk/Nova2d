package io.nova.core.renderer.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public interface Shader {
    void bind();

    void unbind();

    void setUniformMat4f(String name, Matrix4f value);

    void setUniformVec2f(String name, Vector2f value);

    void setUniformInt(String name, int value);

    void setUniformTexture(String name, int slot);

    void setUniformIntArray(String name, int[] values);

    void setUniformTextureArray(String name, int[] slots);

    void setUniformVec4f(String name, Vector4f value);

    void setUniformFloat(String name, float value);
}