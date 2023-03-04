package io.nova.core.renderer.shader;

import io.nova.core.renderer.Renderer;
import io.nova.opengl.renderer.OpenGLShader;

public class ShaderFactory {

    public static Shader create(String path) {
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
}