package io.nova.opengl.renderer;

import io.nova.core.renderer.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLRenderer implements Renderer {

    private final Shader shader;
    private final VertexArray vertexArray;
    private final Texture whiteTexture;

    public OpenGLRenderer() {
        float[] vertices = {
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f
        };

        int[] elementArray = {0, 1, 2, 2, 3, 0};

        vertexArray = new OpenGLVertexArray();

        VertexBuffer vertexBuffer = new OpenGLVertexBuffer(vertices);
        var indexBuffer = new OpenGLIndexBuffer(elementArray);

        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);
        vertexArray.addBuffer(vertexBuffer, layout);
        vertexArray.setIndexBuffer(indexBuffer);

        whiteTexture = Texture.create(1, 1);
        var whiteTextureData = BufferUtils.createByteBuffer(4);
        var oneByte = (byte) 255;
        whiteTextureData.put(new byte[] {oneByte, oneByte, oneByte, oneByte});
        whiteTextureData.flip();
        whiteTexture.setData(whiteTextureData);

        shader = ShaderLibrary.getOrElseUpload("colorAndTexture.glsl");
        shader.bind();
        shader.setUniformTexture("uTexture", 0);
    }

    @Override
    public void beginScene(OrthographicCamera camera) {
        this.shader.bind();
        this.shader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());
    }

    @Override
    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
        shader.setUniformVec4f("uColor", color);
        whiteTexture.bind();
        drawQuad(position, size);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Texture texture, float tilingFactor) {
        shader.setUniformVec4f("uColor", new Vector4f(1.0f));
        shader.setUniformFloat("uTilingFactor", tilingFactor);
        texture.bind();
        drawQuad(position, size);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Vector4f color) {
        shader.setUniformVec4f("uColor", color);
        whiteTexture.bind();
        drawRotatedQuad(position, size, rotation);
    }

    @Override
    public void drawRotatedQuad(Vector3f position, Vector2f size, float rotation, Texture texture, float tilingFactor) {
        shader.setUniformVec4f("uColor", new Vector4f(1.0f));
        shader.setUniformFloat("uTilingFactor", tilingFactor);
        texture.bind();
        drawRotatedQuad(position, size, rotation);
    }

    private void drawQuad(Vector3f position, Vector2f size) {
        vertexArray.bind();
        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);
        shader.setUniformMat4f("uModel", transform);
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }

    private void drawRotatedQuad(Vector3f position, Vector2f size, float rotation) {
        vertexArray.bind();
        var transform = new Matrix4f()
                .translate(position)
                .rotate((float) Math.toRadians(rotation), new Vector3f(0.0f, 0.0f, 1.0f))
                .scale(size.x, size.y, 1.0f);
        shader.setUniformMat4f("uModel", transform);
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }
}