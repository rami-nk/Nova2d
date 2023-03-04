package io.nova.opengl.renderer;

import io.nova.core.renderer.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLRenderer implements Renderer {

    private OrthographicCamera camera;
    private final Shader flatColorShader;
    private final Shader textureShader;
    private final VertexArray vertexArray;

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
        flatColorShader = ShaderLibrary.getOrElseUpload("sandbox2d.glsl");
        textureShader = ShaderLibrary.getOrElseUpload("simple.glsl");
        this.textureShader.bind();
        this.textureShader.setUniformTexture("uTexture", 0);
    }

    @Override
    public void beginScene(OrthographicCamera camera) {
        this.camera = camera;
        this.flatColorShader.bind();
        this.flatColorShader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());

        this.textureShader.bind();
        this.textureShader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());
    }

    @Override
    public void setClearColor(float red, float green, float blue, float alpha) {
        glClearColor(red, green, blue, alpha);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void submit(final VertexArray vertexArray, final Shader shader, Matrix4f transform) {
        shader.bind();
        shader.setUniformMat4f("uViewProjection", camera.getViewProjectionMatrix());
        shader.setUniformMat4f("uModel", transform);

        vertexArray.bind();
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void submit(final VertexArray vertexArray, final Shader shader, int count) {
        shader.bind();
        vertexArray.bind();
        glDrawArrays(GL_TRIANGLES, 0, count);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
        this.flatColorShader.bind();
        this.flatColorShader.setUniformVec4f("uColor", color);
        vertexArray.bind();
        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);
        this.flatColorShader.setUniformMat4f("uModel", transform);
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void drawQuad(Vector3f position, Vector2f size, Texture2d texture2d) {
        textureShader.bind();
        vertexArray.bind();
        var transform = new Matrix4f()
                .translate(position)
                .scale(size.x, size.y, 1.0f);
        this.textureShader.setUniformMat4f("uModel", transform);
        texture2d.bind();
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
    }
}