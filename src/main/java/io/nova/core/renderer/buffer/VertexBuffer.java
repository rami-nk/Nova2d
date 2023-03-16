package io.nova.core.renderer.buffer;

public interface VertexBuffer {

    void reBufferData(float[] data);

    void bind();

    void unbind();
}