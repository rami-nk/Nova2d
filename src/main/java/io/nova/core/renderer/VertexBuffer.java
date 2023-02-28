package io.nova.core.renderer;

public interface VertexBuffer {
    void reBufferData(float[] data);

    void bind();

    void unbind();
}
