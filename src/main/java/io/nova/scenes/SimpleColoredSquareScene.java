package io.nova.scenes;

import io.nova.core.renderer.*;
import io.nova.core.Scene;
import io.nova.opengl.renderer.OpenGLIndexBuffer;
import io.nova.opengl.renderer.OpenGLVertexArray;
import io.nova.opengl.renderer.OpenGLVertexBuffer;
import io.nova.opengl.renderer.OpenGLVertexBufferLayout;
import io.nova.utils.ShaderProvider;
import io.nova.window.Input;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static io.nova.core.codes.KeyCodes.*;

public class SimpleColoredSquareScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;
    private final IndexBuffer indexBuffer;
    private final Camera camera;
    private final Renderer renderer;

    SimpleColoredSquareScene() {
        renderer = Renderer.create();
        camera = new Camera();

        float[] vertices = {
                -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 1.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f,
        };
        int[] elementArray = {3, 2, 0, 0, 2, 1};

        vertexArray = new OpenGLVertexArray();
        VertexBuffer vertexBuffer = new OpenGLVertexBuffer(vertices);
        indexBuffer = new OpenGLIndexBuffer(elementArray);

        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(4);
        vertexArray.addBuffer(vertexBuffer, layout);
        vertexArray.setIndexBuffer(indexBuffer);
        shader = ShaderProvider.getOrElseUploadShader("default.glsl");
    }

    @Override
    public void update(double deltaTime) {
        var speed = 100;
        if (Input.isKeyPressed(NV_KEY_RIGHT)) {
            camera.move(new Vector2f((float)(-deltaTime * speed), 0));
        } else if (Input.isKeyPressed(NV_KEY_UP)) {
            camera.move(new Vector2f(0, (float)(-deltaTime * speed)));
        } else if (Input.isKeyPressed(NV_KEY_DOWN)) {
            camera.move(new Vector2f(0, (float)(deltaTime * speed)));
        } else if (Input.isKeyPressed(NV_KEY_LEFT)) {
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
        renderer.submit(vertexArray, shader);
    }
}