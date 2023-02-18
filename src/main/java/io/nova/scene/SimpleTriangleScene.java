package io.nova.scene;

import io.nova.*;
import io.nova.shader.Shader;

public class SimpleTriangleScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;

    SimpleTriangleScene() {
        float[] vertices =
                {
                        -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                        0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                        0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                };
        VertexBuffer vertexBuffer = new VertexBuffer(vertices);
        vertexBuffer.bind();
        vertexArray = new VertexArray();
        var layout = new VertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(4);
        vertexArray.addBuffer(vertexBuffer, layout);
        vertexArray.bind();
        shader = new Shader("src/main/resources/shaders/simpleTriangle.glsl");
        shader.bind();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        Renderer.draw(vertexArray, shader, 3);
    }
}