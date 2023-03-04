import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.*;
import io.nova.event.Event;
import io.nova.opengl.renderer.OpenGLIndexBuffer;
import io.nova.opengl.renderer.OpenGLVertexArray;
import io.nova.opengl.renderer.OpenGLVertexBuffer;
import io.nova.opengl.renderer.OpenGLVertexBufferLayout;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Sandbox2d extends Layer {

    private OrthographicCameraController cameraController;
    private Renderer renderer;
    private Shader shader;
    private VertexArray vertexArray;

    private Vector3f backgroundColor;
    private final float[] objectColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};

    @Override
    public void onAttach() {
        cameraController = new OrthographicCameraController(1.0f, true);
        renderer = Renderer.create();

        backgroundColor = new Vector3f(1.0f, 1.0f, 1.0f);

        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
                -0.5f, 0.5f, 0.0f
        };

        int[] elementArray = {0, 1, 2, 2, 3, 0};

        vertexArray = new OpenGLVertexArray();

        VertexBuffer vertexBuffer = new OpenGLVertexBuffer(vertices);
        var indexBuffer = new OpenGLIndexBuffer(elementArray);

        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat(3);
        vertexArray.addBuffer(vertexBuffer, layout);
        vertexArray.setIndexBuffer(indexBuffer);
        shader = ShaderLibrary.getOrElseUpload("sandbox2d.glsl");
        shader.setUniformTexture("uTexture", 0);
    }

    @Override
    public void onUpdate(float deltaTime) {
        cameraController.onUpdate(deltaTime);

        renderer.setClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 0.0f);
        renderer.clear();

        shader.setUniformVec4f("uColor", new Vector4f(objectColor));

        renderer.beginScene(cameraController.getCamera());
        {
            renderer.submit(vertexArray, shader);
        }
        renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }

    @Override
    public void onImGuiRender() {
        ImGui.colorEdit4("Object color", objectColor);
    }
}