package io.nova;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL30.*;

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