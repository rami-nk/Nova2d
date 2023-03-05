import imgui.ImGui;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.RendererFactory;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.core.renderer.texture.TextureLibrary;
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
        cameraController.onUpdate(deltaTime);

        renderer.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        renderer.clear();

        renderer.beginScene(cameraController.getCamera());
        {
            renderer.drawQuad(new Vector2f(), new Vector2f(1.0f, 1.0f), new Vector4f(objectColor));
            renderer.drawQuad(new Vector2f(0.5f, 0.5f), new Vector2f(1.0f, 1.0f), new Vector4f(0.5f));
            renderer.drawQuad(new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f), TextureLibrary.getOrElseUploadTexture("Nova2d-logo-white.png"));
            renderer.drawQuad(new Vector2f(-0.5f, -0.5f), new Vector2f(2.0f, 2.5f), TextureLibrary.getOrElseUploadTexture("openGlLogo.png"), 10.0f);
            renderer.drawRotatedQuad(
                    new Vector2f(-0.5f, -0.5f),
                    new Vector2f(2.0f, 2.5f),
                    45.0f,
                    TextureLibrary.getOrElseUploadTexture("openGlLogo.png"),
                    10.0f,
                    new Vector4f(0.2f, 0.1f, 0.8f, 0.5f));
        }
        renderer.endScene();
    }

    @Override
    public void onImGuiRender() {
        ImGui.colorEdit4("Object color", objectColor);
    }

    @Override
    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }
}