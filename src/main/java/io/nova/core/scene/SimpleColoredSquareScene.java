package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.Renderer;
import io.nova.core.buffer.IndexBuffer;
import io.nova.core.buffer.VertexArray;
import io.nova.core.buffer.VertexBuffer;
import io.nova.core.buffer.VertexBufferLayout;
import io.nova.core.listener.KeyListener;
import io.nova.core.shader.Shader;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class SimpleColoredSquareScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;
    private final IndexBuffer indexBuffer;
    private final Camera camera;

    SimpleColoredSquareScene() {
        camera = new Camera(500, 500, new Vector2f());
        float[] vertices = {
                300, 200, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
                200, 300, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                300, 300, 0, 1.0f, 0.0f, 0.0f, 1.0f,
                200, 200, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
        };
        int[] elementArray = {2, 1, 0, 0, 1, 3};

        vertexArray = new VertexArray();
        VertexBuffer vertexBuffer = new VertexBuffer(vertices);
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
        var speed = 100;
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.getPosition().x -= deltaTime * speed;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            camera.getPosition().y -= deltaTime * speed;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.getPosition().y += deltaTime * speed;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.getPosition().x += deltaTime * speed;
        }

        shader.setUniformMat4f("uProjection", camera.getProjectionMatrix());
        shader.setUniformMat4f("uView", camera.getViewMatrix());
    }

    @Override
    public void render() {
        Renderer.draw(vertexArray, indexBuffer, shader);
    }
}