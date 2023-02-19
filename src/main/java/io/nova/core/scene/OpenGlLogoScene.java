package io.nova.core.scene;

import io.nova.core.Renderer;
import io.nova.core.Texture;
import io.nova.core.buffer.IndexBuffer;
import io.nova.core.buffer.VertexArray;
import io.nova.core.buffer.VertexBuffer;
import io.nova.core.buffer.VertexBufferLayout;
import io.nova.core.shader.Shader;

import static org.lwjgl.opengl.GL30.GL_TEXTURE0;

public class OpenGlLogoScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;
    private final IndexBuffer indexBuffer;
    private final Texture texture;

    OpenGlLogoScene() {
        float[] vertices = {
                0.5f, -0.5f, 0, 1, 1,
                -0.5f, 0.5f, 0, 0, 0,
                0.5f, 0.5f, 0, 1, 0,
                -0.5f, -0.5f, 0, 0, 1,
        };

        int[] elementArray = {2, 1, 0, 0, 1, 3};

        vertexArray = new VertexArray();
        VertexBuffer vertexBuffer = new VertexBuffer(vertices);
        indexBuffer = new IndexBuffer(elementArray);

        var layout = new VertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);

        vertexArray.addBuffer(vertexBuffer, layout);

        shader = new Shader("src/main/resources/shaders/openGlLogo.glsl");
        texture = new Texture("src/main/resources/textures/openGlLogo.png");

        shader.setUniformTexture("u_Texture", 0);
        texture.activate(GL_TEXTURE0);
        texture.bind();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        Renderer.draw(vertexArray, indexBuffer, shader);
    }
}