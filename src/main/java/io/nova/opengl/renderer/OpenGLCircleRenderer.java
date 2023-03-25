package io.nova.opengl.renderer;

import io.nova.core.renderer.buffer.*;
import io.nova.core.renderer.shader.Shader;
import io.nova.core.renderer.shader.ShaderLibrary;
import io.nova.ecs.component.CircleRendererComponent;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.util.Arrays;

import static io.nova.opengl.renderer.OpenGLRenderer.*;
import static org.lwjgl.opengl.GL11.*;

public class OpenGLCircleRenderer {

    private final Shader shader;
    private final VertexBuffer vertexBuffer;
    private final VertexArray vertexArray;
    private final Vector4f[] vertexPositions;
    private final Runnable endSceneCallback;
    private float[] data;
    private int indexCount;
    private int dataIndex;
    private int elementsPerVertex;

    public OpenGLCircleRenderer(int[] indices, Runnable endSceneCallback) {
        this.endSceneCallback = endSceneCallback;

        vertexArray = VertexArrayFactory.create();
        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat("aWorldPosition", 3);
        layout.pushFloat("aLocalPosition", 3);
        layout.pushFloat("aColor", 4);
        layout.pushFloat("aThickness", 1);
        layout.pushFloat("aFade", 1);
        // Editor only
        layout.pushFloat("aEntityID", 1);
        elementsPerVertex = layout.getCount();
        vertexBuffer = VertexBufferFactory.create(MAX_ELEMENTS * VERTICES_PER_OBJECT * elementsPerVertex);
        vertexArray.addVertexBuffer(vertexBuffer, layout);

        var indexBuffer = IndexBufferFactory.create(indices);
        vertexArray.setIndexBuffer(indexBuffer);

        shader = ShaderLibrary.getOrElseUpload("Nova2d_Circle_Shader.glsl");
        shader.bind();

        vertexPositions = new Vector4f[]{
                new Vector4f(-0.5f, -0.5f, 0.0f, 1.0f),
                new Vector4f(0.5f, -0.5f, 0.0f, 1.0f),
                new Vector4f(0.5f, 0.5f, 0.0f, 1.0f),
                new Vector4f(-0.5f, 0.5f, 0.0f, 1.0f)
        };
    }

    public void drawCircle(Matrix4f transform, CircleRendererComponent component, int entityID) {
        addData(transform, component.getColorAsVec(), component.getThickness(), component.getFade(), entityID);
    }

    void resetData() {
        data = new float[MAX_ELEMENTS * elementsPerVertex * VERTICES_PER_OBJECT];
        indexCount = 0;
        dataIndex = 0;
    }

    private void addData(Matrix4f transform, Vector4f color, float thickness, float fade, int entityID) {
        if (indexCount >= MAX_INDICES) {
            endSceneCallback.run();
            resetData();
        }
        for (int i = 0; i < 4; i++) {
            Vector4f transformedPos = vertexPositions[i].mul(transform, new Vector4f());

            data[dataIndex++] = transformedPos.x;
            data[dataIndex++] = transformedPos.y;
            data[dataIndex++] = transformedPos.z;

            data[dataIndex++] = vertexPositions[i].x * 2.0f;
            data[dataIndex++] = vertexPositions[i].y * 2.0f;
            data[dataIndex++] = vertexPositions[i].z * 2.0f;

            data[dataIndex++] = color.x;
            data[dataIndex++] = color.y;
            data[dataIndex++] = color.z;
            data[dataIndex++] = color.w;

            data[dataIndex++] = thickness;
            data[dataIndex++] = fade;
            data[dataIndex++] = entityID;
        }
        indexCount += 6;
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
            glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
            stats.drawCalls++;
        }
    }
}