package io.nova.scenes;

import io.nova.core.Scene;
import io.nova.renderer.Renderer;
import io.nova.renderer.Texture2d;
import io.nova.renderer.IndexBuffer;
import io.nova.renderer.VertexArray;
import io.nova.renderer.VertexBuffer;
import io.nova.renderer.VertexBufferLayout;
import io.nova.renderer.Shader;
import io.nova.utils.ShaderProvider;
import io.nova.utils.TextureProvider;

import static org.lwjgl.opengl.GL30.GL_TEXTURE0;

public class OpenGlLogoScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;
    private final IndexBuffer indexBuffer;
    private final Texture2d texture;

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

        shader = ShaderProvider.getOrElseUploadShader("simpleTexture.glsl");
        texture = TextureProvider.getOrElseUploadTexture("openGlLogo.png");

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