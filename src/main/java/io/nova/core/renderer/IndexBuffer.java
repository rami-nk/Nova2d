package io.nova.core.renderer;

public interface IndexBuffer {
    void bind();

    void unbind();

    int getCount();
}
