import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import io.nova.core.application.Application;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.*;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.ecs.Scene;
import io.nova.ecs.component.CameraComponent;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;
import io.nova.event.Event;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class EcsLayer extends Layer {

    private Scene scene;
    private Renderer renderer;
    private OrthographicCameraController cameraController;
    private Entity entity;
    private Entity primaryCamera;
    private Entity secondaryCamera;
    private boolean primary = true;
    private float[] cameraPosition = new float[3];
    private Vector2f viewportSize = new Vector2f();

    private FrameBuffer frameBuffer;

    public void onAttach() {
        cameraController = new OrthographicCameraController(16.0f / 9.0f, true);
        renderer = RendererFactory.create();
        scene = new Scene(renderer);

        entity = scene.createEntity();
        entity.addComponent(new SpriteRenderComponent());

        primaryCamera = scene.createEntity();
        primaryCamera.addComponent(new CameraComponent());
        primaryCamera.getComponent(CameraComponent.class).setPrimary(primary);

        secondaryCamera = scene.createEntity();
        secondaryCamera.addComponent(new CameraComponent(new Matrix4f().ortho(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f)));
        secondaryCamera.getComponent(CameraComponent.class).setPrimary(!primary);

        scene.activateEntities(entity, primaryCamera, secondaryCamera);

        var spec = new FrameBufferSpecification(Application.getWindow().getWidth(), Application.getWindow().getHeight());
        frameBuffer = FrameBufferFactory.create(spec);
    }

    public void onDetach() {
        scene.dispose();
        frameBuffer.dispose();
    }

    public void onUpdate(float deltaTime) {
        cameraController.onUpdate(deltaTime);

        frameBuffer.bind();
        renderer.resetStats();
        renderer.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        renderer.clear();

        scene.onUpdate(deltaTime);
        frameBuffer.unbind();
    }

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

        {
            ImGui.text(entity.getComponent(TagComponent.class).getTag());
            if (ImGui.checkbox(primary ? "Primary" : "Secondary", primary)) {
                primary = !primary;
                primaryCamera.getComponent(CameraComponent.class).setPrimary(primary);
                secondaryCamera.getComponent(CameraComponent.class).setPrimary(!primary);
            }

            ImGui.dragFloat3("Camera Position", cameraPosition);
            var transformComponent = primaryCamera.getComponent(TransformComponent.class);
            transformComponent.setTransform(new Matrix4f().translate(new Vector3f(cameraPosition), new Matrix4f()));
        }

        ImGui.end();
    }

    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }
}