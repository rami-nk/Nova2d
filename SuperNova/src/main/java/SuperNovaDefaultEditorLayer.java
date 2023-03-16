import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import io.nova.core.application.Application;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.*;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureLibrary;
import io.nova.event.Event;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class SuperNovaDefaultEditorLayer extends Layer {

    private OrthographicCameraController cameraController;
    private Renderer renderer;

    private Texture texture;
    private SubTexture grass;
    private SubTexture stone;
    private SubTexture ceiling;
    private FrameBuffer frameBuffer;
    private Vector2f viewportSize = new Vector2f();

    @Override
    public void onAttach() {
        cameraController = new OrthographicCameraController(1000.0f / 600.0f, true);
        renderer = RendererFactory.create();
        texture = TextureLibrary.getOrElseUploadTexture("tilemap_packed.png");
        grass = new SubTexture(texture, new Vector2f(0, 10), new Vector2f(16.0f, 16.0f));
        stone = new SubTexture(texture, new Vector2f(1, 1), new Vector2f(16.0f, 16.0f));
        ceiling = new SubTexture(texture, new Vector2f(6, 6), new Vector2f(16.0f, 16.0f));

        var spec = new FrameBufferSpecification(Application.getWindow().getWidth(), Application.getWindow().getHeight());
        frameBuffer = FrameBufferFactory.create(spec);
    }

    @Override
    public void onDetach() {
        frameBuffer.dispose();
    }

    @Override
    public void onUpdate(float deltaTime) {
        cameraController.onUpdate(deltaTime);

        frameBuffer.bind();
        renderer.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        renderer.clear();

        renderer.resetStats();
        renderer.beginScene(cameraController.getCamera());
        {
            renderer.drawQuad(new Vector3f(), new Vector2f(1.0f, 1.0f), grass);
            renderer.drawQuad(new Vector2f(1.0f, 0.0f), new Vector2f(1.0f, 1.0f), stone);
            renderer.drawRotatedQuad(new Vector2f(2.0f, 0.0f), new Vector2f(1.0f, 1.0f), (float) Math.toRadians(45.0f), ceiling);
        }
        renderer.endScene();
        frameBuffer.unbind();
    }

    @Override
    public void onImGuiRender() {

        // We are using the ImGuiWindowFlags_NoDocking flag to make the parent window not dockable into,
        // because it would be confusing to have two docking targets within each others.
        var window_flags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;
        var viewport = ImGui.getMainViewport();

        ImGui.setNextWindowPos(viewport.getPosX(), viewport.getPosY(), ImGuiCond.Always);
        ImGui.setNextWindowSize(viewport.getWorkSizeX(), viewport.getWorkSizeY());
        ImGui.setNextWindowViewport(viewport.getID());

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);

        window_flags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove;
        window_flags |= ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

        ImGui.begin("Dockspace", window_flags);

        ImGui.popStyleVar(2);

        ImGui.dockSpace(ImGui.getID("Dockspace"));

        ImGui.beginMenuBar();
        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Exit")) {
                Application.getInstance().close();
            }
            ImGui.endMenu();
        }
        ImGui.endMenuBar();

        ImGui.begin("Renderer stats");
        {
            var stats = renderer.getStats();
            ImGui.text("Draw calls: " + stats.getDrawCalls());
            ImGui.text("Quad count: " + stats.getQuadCount());
            ImGui.text("Vertex count: " + stats.getTotalVertexCount());
            ImGui.text("Index count: " + stats.getTotalIndexCount());
        }
        ImGui.end();

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
        ImGui.begin("Viewport");
        {
            var viewportSize = ImGui.getContentRegionAvail();
            if (viewportSize.x != this.viewportSize.x || viewportSize.y != this.viewportSize.y) {
                frameBuffer.resize((int) viewportSize.x, (int) viewportSize.y);
                this.viewportSize.x = viewportSize.x;
                this.viewportSize.y = viewportSize.y;
            }

            var textureId = frameBuffer.getColorAttachmentRendererId();
            ImGui.image(textureId, viewportSize.x, viewportSize.y, 0, 1, 1, 0);
            // We set the viewPortSize of the cameraController here to make sure the aspect ratio is correct after resizing the window
            cameraController.setViewportSize((int) viewportSize.x, (int) viewportSize.y);
        }
        ImGui.end();
        ImGui.popStyleVar();

        ImGui.end();
    }

    @Override
    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }
}