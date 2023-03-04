package io.nova.core.renderer.buffer;

import java.util.List;

public interface VertexBufferLayout {
    void pushFloat(int count);

    void pushInt(int count);

    List<VertexBufferElement> getElements();

    int getStride();
}