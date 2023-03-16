package io.nova.core.renderer.buffer;

import io.nova.core.renderer.Renderer;
import io.nova.opengl.renderer.OpenGLVertexBuffer;

public class VertexBufferFactory {

    public static VertexBuffer create(float[] data) {
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

    public static VertexBuffer create(int count) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLVertexBuffer(count);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }
}