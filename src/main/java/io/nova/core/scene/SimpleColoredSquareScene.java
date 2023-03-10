package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.buffer.IndexBuffer;
import io.nova.core.buffer.VertexArray;
import io.nova.core.buffer.VertexBuffer;
import io.nova.core.buffer.VertexBufferLayout;
import io.nova.core.listener.KeyListener;
import io.nova.core.renderer.Renderer;
import io.nova.core.shader.Shader;
import io.nova.core.utils.ShaderProvider;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class SimpleColoredSquareScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;
    private final IndexBuffer indexBuffer;
    private final Camera camera;

    SimpleColoredSquareScene() {
        camera = new Camera();

        float[] vertices = {
                -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 1.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f,
        };
        int[] elementArray = {3, 2, 0, 0, 2, 1};

        vertexArray = new VertexArray();
        VertexBuffer vertexBuffer = new VertexBuffer(vertices);
        indexBuffer = new IndexBuffer(elementArray);

        var layout = new VertexBufferLayout();
        layout.pushFloat(2);
        layout.pushFloat(4);
        vertexArray.addBuffer(vertexBuffer, layout);
        shader = ShaderProvider.getOrElseUploadShader("default.glsl");
    }

    @Override
    public void update(double deltaTime) {
        var speed = 100;
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.move(new Vector2f((float)(-deltaTime * speed), 0));
        } else if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            camera.move(new Vector2f(0, (float)(-deltaTime * speed)));
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.move(new Vector2f(0, (float)(deltaTime * speed)));
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.move(new Vector2f((float)(deltaTime * speed), 0));
        }

        var positionOfObjectInWorld = new Vector2f(0, 0);
        var scaleOfObjectInWorld = new Vector2f(400, 400);

        var modelMatrix = new Matrix4f()
                .translate(new Vector3f(positionOfObjectInWorld, 0))
                .scale(new Vector3f(scaleOfObjectInWorld, 1));

        shader.setUniformMat4f("uProjection", camera.getProjectionMatrix());
        shader.setUniformMat4f("uModel", modelMatrix);
    }

    @Override
    public void render() {
        Renderer.draw(vertexArray, indexBuffer, shader);
    }
}