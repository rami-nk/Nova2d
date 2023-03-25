package io.nova.opengl.renderer;

import io.nova.core.renderer.buffer.VertexArray;
import io.nova.core.renderer.buffer.VertexArrayFactory;
import io.nova.core.renderer.buffer.VertexBuffer;
import io.nova.core.renderer.buffer.VertexBufferFactory;
import io.nova.core.renderer.shader.Shader;
import io.nova.core.renderer.shader.ShaderLibrary;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Arrays;

import static io.nova.opengl.renderer.OpenGLRenderer.*;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLLineRenderer {

    private final Shader shader;
    private final VertexBuffer vertexBuffer;
    private final VertexArray vertexArray;
    private final Runnable endSceneCallback;
    private final int elementsPerVertex;
    private float[] data;
    private int vertexCount;
    private int dataIndex;
    private float lineWidth;

    public OpenGLLineRenderer(Runnable endSceneCallback) {
        this.endSceneCallback = endSceneCallback;

        vertexArray = VertexArrayFactory.create();
        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat("aPosition", 3);
        layout.pushFloat("aColor", 4);
        elementsPerVertex = layout.getCount();
        vertexBuffer = VertexBufferFactory.create(MAX_ELEMENTS * VERTICES_PER_OBJECT * elementsPerVertex);
        vertexArray.addVertexBuffer(vertexBuffer, layout);

        shader = ShaderLibrary.getOrElseUpload("Nova2d_Line_Shader.glsl");
        shader.bind();
        lineWidth = 1.0f;
    }

    public void drawLine(Vector3f p1, Vector3f p2, Vector4f color) {
        addData(p1, p2, color);
    }

    void resetData() {
        data = new float[MAX_ELEMENTS * elementsPerVertex * VERTICES_PER_OBJECT];
        vertexCount = 0;
        dataIndex = 0;
    }

    private void addData(Vector3f p1, Vector3f p2, Vector4f color) {
        if (vertexCount >= MAX_INDICES) {
            endSceneCallback.run();
            resetData();
        }

        var arr = new Vector3f[]{p1, p2};

        for (var p : arr) {
            data[dataIndex++] = p.x;
            data[dataIndex++] = p.y;
            data[dataIndex++] = p.z;
            data[dataIndex++] = color.x;
            data[dataIndex++] = color.y;
            data[dataIndex++] = color.z;
            data[dataIndex++] = color.w;
        }

        vertexCount += 2;
        stats.quadCount++;
    }

    public void beginScene(Matrix4f viewProjection) {
        shader.bind();
        shader.setUniformMat4f("uViewProjection", viewProjection);

        resetData();
    }

    public void endScene() {
        if (dataIndex > 0) {
            var copy = Arrays.copyOfRange(data, 0, dataIndex);
            vertexBuffer.reBufferData(copy);
        }
    }

    public void flush() {
        if (dataIndex > 0) {
            vertexArray.bind();
            shader.bind();
            glLineWidth(lineWidth);
            glDrawArrays(GL_LINES, 0, vertexCount);
            stats.drawCalls++;
        }
    }


    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}
