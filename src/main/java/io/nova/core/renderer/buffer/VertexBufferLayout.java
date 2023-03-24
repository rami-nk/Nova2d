package io.nova.core.renderer.buffer;

import java.util.List;

public interface VertexBufferLayout {
    void pushFloat(String name, int count);

    void pushInt(String name, int count);

    List<VertexBufferElement> getElements();

    int getStride();

    int getCount();
}