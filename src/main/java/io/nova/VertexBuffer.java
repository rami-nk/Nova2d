package io.nova;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {

    private final int rendererId;

    public VertexBuffer(final float[] data) {
       rendererId = glGenBuffers();
       bind();
       glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, rendererId);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}