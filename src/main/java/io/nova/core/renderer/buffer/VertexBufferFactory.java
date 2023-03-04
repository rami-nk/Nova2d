package io.nova.core.renderer.buffer;

import io.nova.core.renderer.Renderer;
import io.nova.opengl.renderer.OpenGLVertexBuffer;

public class VertexBufferFactory {

    static VertexBuffer create(float[] data) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLVertexBuffer(data);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }

    static VertexBuffer create(int size) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLVertexBuffer(size);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }
}