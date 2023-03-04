package io.nova.core.renderer.buffer;

import io.nova.core.renderer.Renderer;
import io.nova.opengl.renderer.OpenGLVertexArray;

public class VertexArrayFactory {

    static VertexArray create() {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLVertexArray();
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }
}