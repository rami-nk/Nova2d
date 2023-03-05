package io.nova.core.renderer.buffer;

import io.nova.core.renderer.Renderer;
import io.nova.opengl.renderer.OpenGLIndexBuffer;

public class IndexBufferFactory {

    public static IndexBuffer create(int[] data) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLIndexBuffer(data);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }
}