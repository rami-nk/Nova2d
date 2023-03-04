package io.nova.core.renderer.texture;

import io.nova.core.renderer.Renderer;
import io.nova.opengl.renderer.OpenGLTexture;

public class TextureFactory {

    public static Texture create(String path) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLTexture(path);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }

    public static Texture create(int width, int height) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLTexture(width, height);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }
}