package io.nova.scenes;

import io.nova.core.Scene;
import io.nova.core.renderer.*;
import io.nova.opengl.renderer.OpenGLIndexBuffer;
import io.nova.opengl.renderer.OpenGLVertexArray;
import io.nova.opengl.renderer.OpenGLVertexBuffer;
import io.nova.opengl.renderer.OpenGLVertexBufferLayout;
import io.nova.core.renderer.ShaderLibrary;
import io.nova.core.renderer.TextureLibrary;

import static org.lwjgl.opengl.GL30.GL_TEXTURE0;

public class OpenGlLogoScene extends Scene {

    private final VertexArray vertexArray;
    private final Shader shader;
    private final IndexBuffer indexBuffer;
    private final Texture texture;
    private final Renderer renderer;

    OpenGlLogoScene() {
        renderer = Renderer.create();

        float[] vertices = {
                0.5f, -0.5f, 0, 1, 1,
                -0.5f, 0.5f, 0, 0, 0,
                0.5f, 0.5f, 0, 1, 0,
                -0.5f, -0.5f, 0, 0, 1,
        };

        int[] elementArray = {2, 1, 0, 0, 1, 3};

        vertexArray = new OpenGLVertexArray();
        VertexBuffer vertexBuffer = new OpenGLVertexBuffer(vertices);
        indexBuffer = new OpenGLIndexBuffer(elementArray);

        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);

        vertexArray.addBuffer(vertexBuffer, layout);
        vertexArray.setIndexBuffer(indexBuffer);

        shader = ShaderLibrary.getOrElseUpload("simpleTexture.glsl");
        texture = TextureLibrary.getOrElseUploadTexture("openGlLogo.png");

        shader.setUniformTexture("u_Texture", 0);
        texture.activate(GL_TEXTURE0);
        texture.bind();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        // TODO: use current API
//        renderer.submit(vertexArray, shader);
    }
}