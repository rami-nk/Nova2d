package io.nova.opengl.renderer;

import io.nova.core.renderer.buffer.VertexBuffer;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLVertexBuffer implements VertexBuffer {

    private final int rendererId;
    private final int usage;

    public OpenGLVertexBuffer(int count) {
        rendererId = glGenBuffers();
        bind();
        usage = GL_DYNAMIC_DRAW;
        glBufferData(GL_ARRAY_BUFFER, new float[count], usage);
    }

    public OpenGLVertexBuffer(final float[] data) {
        rendererId = glGenBuffers();
        bind();
        usage = GL_STATIC_DRAW;
        glBufferData(GL_ARRAY_BUFFER, data, usage);
    }

    public OpenGLVertexBuffer(final float[] data, int usage) {
        rendererId = glGenBuffers();
        bind();
        this.usage = usage;
        glBufferData(GL_ARRAY_BUFFER, data, usage);
    }

    @Override
    public void reBufferData(float[] data) {
        this.bind();
        glBufferSubData(GL_ARRAY_BUFFER, 0, data);
    }

    @Override
    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, rendererId);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}