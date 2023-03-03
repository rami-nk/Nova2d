import io.nova.core.layer.Layer;
import io.nova.core.renderer.*;
import io.nova.event.Event;
import io.nova.opengl.renderer.OpenGLIndexBuffer;
import io.nova.opengl.renderer.OpenGLVertexArray;
import io.nova.opengl.renderer.OpenGLVertexBuffer;
import io.nova.opengl.renderer.OpenGLVertexBufferLayout;
import io.nova.utils.ShaderProvider;
import io.nova.window.Input;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static io.nova.core.codes.KeyCodes.*;

public class Sandbox2d extends Layer {

    private OrthographicCamera camera;
    private Renderer renderer;
    private Shader shader;
    private VertexArray vertexArray;

    private Vector3f color;
    private float rotation = 0.0f;
    private Vector3f position;

    @Override
    public void onAttach() {
        camera = new OrthographicCamera(-1.0f, 1.0f, -1.0f, 1.0f);
        renderer = Renderer.create();

        position = new Vector3f(0.0f);
        rotation = 0.0f;
        color = new Vector3f(0.5f, 0.5f, 0.5f);

        float[] vertices = {
                -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
        };
        int[] elementArray = {0, 1, 2, 2, 3, 0};

        vertexArray = new OpenGLVertexArray();

        VertexBuffer vertexBuffer = new OpenGLVertexBuffer(vertices);
        var indexBuffer = new OpenGLIndexBuffer(elementArray);

        var layout = new OpenGLVertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(4);
        vertexArray.addBuffer(vertexBuffer, layout);
        vertexArray.setIndexBuffer(indexBuffer);
        shader = ShaderProvider.getOrElseUploadShader("simple.glsl");
    }

    @Override
    public void onUpdate(float deltaTime) {
        renderer.setClearColor(color.x, color.y, color.z, 0.0f);
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

        renderer.beginScene(camera);
        {
            for (int y = 0; y < 20; y++) {
                for (int x = 0; x < 20; x++) {
                    var position = new Vector3f(x * 0.11f, y * 0.11f, 0.0f);
                    var transform = new Matrix4f()
                            .translate(position)
                            .scale(0.1f);
                    renderer.submit(vertexArray, shader, transform);
                }
            }
        }
        renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {
    }

    @Override
    public void onImGuiRender() {
    }
}