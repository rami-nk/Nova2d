package io.nova.scene;

import io.nova.*;
import io.nova.shader.Shader;

public class SimpleColoredSquareScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;
    private final IndexBuffer indexBuffer;

    SimpleColoredSquareScene() {
        vertexArray = new VertexArray();
        float[] vertices = {
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
                -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
        };
        VertexBuffer vertexBuffer = new VertexBuffer(vertices);
        int[] elementArray = {2, 1, 0, 0, 1, 3};
        indexBuffer = new IndexBuffer(elementArray);

        var layout = new VertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(4);
        vertexArray.addBuffer(vertexBuffer, layout);
        shader = new Shader("src/main/resources/shaders/default.glsl");
        shader.bind();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        Renderer.draw(vertexArray, indexBuffer, shader);
    }
}