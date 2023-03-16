package io.nova.core.renderer.buffer;

public interface IndexBuffer {
    void bind();

    void unbind();

    int getCount();
}
