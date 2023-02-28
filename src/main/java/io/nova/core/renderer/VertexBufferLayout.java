package io.nova.core.renderer;

import java.util.List;

public interface VertexBufferLayout {
    void pushFloat(int count);

    void pushInt(int count);

    List<VertexBufferElement> getElements();

    int getStride();
}