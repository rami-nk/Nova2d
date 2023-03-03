import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.*;
import io.nova.event.Event;
import io.nova.opengl.renderer.OpenGLIndexBuffer;
import io.nova.opengl.renderer.OpenGLVertexArray;
import io.nova.opengl.renderer.OpenGLVertexBuffer;
import io.nova.opengl.renderer.OpenGLVertexBufferLayout;
import io.nova.core.renderer.ShaderLibrary;
import io.nova.core.renderer.TextureLibrary;
import io.nova.window.Input;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static io.nova.core.codes.KeyCodes.*;

public class Sandbox2d extends Layer {

    private OrthographicCamera camera;
    private Renderer renderer;
    private Shader shader;
    private VertexArray vertexArray;
    private Texture2d texture2d;

    private Vector3f backgroundColor;
    private float[] objectColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private float rotation = 0.0f;
    private Vector3f position;

    @Override
    public void onAttach() {
        camera = new OrthographicCamera(-1.0f, 1.0f, -1.0f, 1.0f);
        renderer = Renderer.create();

        position = new Vector3f(0.0f);
        rotation = 0.0f;
        backgroundColor = new Vector3f(1.0f, 1.0f, 1.0f);

        float[] vertices = {
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
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
        shader = ShaderLibrary.getOrElseUpload("simple.glsl");
        shader.setUniformTexture("uTexture", 0);

        var textureIdx = TextureLibrary.upload("openGlLogo.png");
        texture2d = TextureLibrary.get(textureIdx);
        texture2d.bind();
    }

    @Override
    public void onUpdate(float deltaTime) {
        renderer.setClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 0.0f);
        renderer.clear();
        var cameraSpeed = 0.5 * deltaTime;

        if (Input.isKeyPressed(NV_KEY_LEFT)) {
            position.x -= cameraSpeed;
        } else if (Input.isKeyPressed(NV_KEY_RIGHT)) {
            position.x += cameraSpeed;
        } else if (Input.isKeyPressed(NV_KEY_UP)) {
            position.y += cameraSpeed;
        } else if (Input.isKeyPressed(NV_KEY_DOWN)) {
            position.y -= cameraSpeed;
        }

        camera.setRotation(rotation);
        camera.setPosition(position);

        shader.setUniformVec4f("uColor", new Vector4f(objectColor));

        renderer.beginScene(camera);
        {
            renderer.submit(vertexArray, shader);
        }
        renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {
    }

    @Override
    public void onImGuiRender() {
        ImGui.colorEdit4("Object color", objectColor);
    }
}