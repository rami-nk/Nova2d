package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLRenderer;

public class RendererFactory {

    public static Renderer create() {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLRenderer();
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }
}