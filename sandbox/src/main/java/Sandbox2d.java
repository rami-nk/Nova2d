import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.RendererFactory;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.event.Event;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Sandbox2d extends Layer {

    private final float[] objectColor = new float[]{0.8f, 0.2f, 0.3f, 1.0f};
    private OrthographicCameraController cameraController;
    private Renderer renderer;

    @Override
    public void onAttach() {
        cameraController = new OrthographicCameraController(1000.0f / 600.0f, true);
        renderer = RendererFactory.create();
    }

    @Override
    public void onUpdate(float deltaTime) {
        System.out.println(1.0f / deltaTime);
        cameraController.onUpdate(deltaTime);

        renderer.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        renderer.clear();

        renderer.resetStats();
        renderer.beginScene(cameraController.getCamera());
        {
            for (float y = -5.0f; y < 5.0f; y += 0.1f) {
                for (float x = -5.0f; x < 5.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 0.0f; y < 10.0f; y += 0.1f) {
                for (float x = 0.0f; x < 10.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 5.0f; y < 15.0f; y += 0.1f) {
                for (float x = 5.0f; x < 15.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 10.0f; y < 20.0f; y += 0.1f) {
                for (float x = 10.0f; x < 20.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 10.0f; y < 20.0f; y += 0.1f) {
                for (float x = 10.0f; x < 20.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 10.0f; y < 20.0f; y += 0.1f) {
                for (float x = 10.0f; x < 20.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 10.0f; y < 20.0f; y += 0.1f) {
                for (float x = 10.0f; x < 20.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 10.0f; y < 20.0f; y += 0.1f) {
                for (float x = 10.0f; x < 20.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 10.0f; y < 20.0f; y += 0.1f) {
                for (float x = 10.0f; x < 20.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
            for (float y = 10.0f; y < 20.0f; y += 0.1f) {
                for (float x = 10.0f; x < 20.0f; x += 0.1f) {
                    renderer.drawQuad(new Vector2f(x, y), new Vector2f(0.45f, 0.45f), new Vector4f(objectColor));
                }
            }
        }
        renderer.endScene();
    }

    @Override
    public void onImGuiRender() {
        ImGui.colorEdit4("Object color", objectColor);

        var stats = renderer.getStats();
        ImGui.text("Renderer stats:");
        ImGui.text("Draw calls: " + stats.getDrawCalls());
        ImGui.text("Quad count: " + stats.getQuadCount());
        ImGui.text("Vertex count: " + stats.getTotalVertexCount());
        ImGui.text("Index count: " + stats.getTotalIndexCount());
    }

    @Override
    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }
}