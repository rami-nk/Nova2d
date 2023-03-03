package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public interface Shader {

    static Shader create(String path) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLShader(path);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }

    void bind();

    void unbind();

    void setUniformMat4f(String name, Matrix4f mat4f);

    void setUniformVec2f(String name, Vector2f vec2f);

    void setUniformInt(String name, int value);

    void setUniformTexture(String name, int slot);

    void setUniformIntArray(String name, int[] values);

    void setUniformTextureArray(String name, int[] slots);
    void setUniformVec4f(String name, Vector4f vec4f);
}