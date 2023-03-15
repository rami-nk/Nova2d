package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLFrameBuffer;

public class FrameBufferFactory {

    public static FrameBuffer create(FrameBufferSpecification specification) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLFrameBuffer(specification);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }
}