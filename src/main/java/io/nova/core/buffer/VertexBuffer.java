package io.nova.core.buffer;

import static org.lwjgl.opengl.GL30.*;

public class VertexBuffer {

    private final int rendererId;
    private final int usage;

    public VertexBuffer(final float[] data) {
        rendererId = glGenBuffers();
        bind();
        usage = GL_STATIC_DRAW;
        glBufferData(GL_ARRAY_BUFFER, data, usage);
    }

    public VertexBuffer(final float[] data, int usage) {
        rendererId = glGenBuffers();
        bind();
        this.usage = usage;
        glBufferData(GL_ARRAY_BUFFER, data, usage);
    }

    public void reBufferData(float[] data) {
        glBufferSubData(GL_ARRAY_BUFFER, 0, data);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, rendererId);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}