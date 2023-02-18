package io.nova;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class VertexArray {

    private final int rendererId;

    public VertexArray() {
        rendererId = glGenVertexArrays();
    }

    public void addBuffer(final VertexBuffer vertexBuffer, final VertexBufferLayout vertexBufferLayout) {
        bind();
        vertexBuffer.bind();

        var elements = vertexBufferLayout.getElements();
        int offset = 0;
        int index = 0;
        for (var element : elements) {
            var floatBuffer = BufferUtils.createFloatBuffer(1);
            floatBuffer.put(1.0f);
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index, element.getCount(), element.getType(), element.getNormalized(), offset, floatBuffer);
            offset += element.getCount() * VertexBufferElement.getSizeOfType(element.getType());
            index++;
        }
    }

    public void bind() {
        glBindVertexArray(rendererId);
    }

    public void unbind() {
        glBindVertexArray(0);
    }
}