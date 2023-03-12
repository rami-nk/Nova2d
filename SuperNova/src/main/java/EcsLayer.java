import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import io.nova.core.application.Application;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.*;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.ecs.Scene;
import io.nova.ecs.component.*;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.ScriptableEntity;
import io.nova.event.Event;
import io.nova.window.Input;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static io.nova.core.codes.KeyCodes.*;

public class EcsLayer extends Layer {

    private final float[] cameraPosition = new float[3];
    private final Vector2f viewportSize = new Vector2f();
    private float[] orthographicSize = new float[]{1.0f};
    private Scene scene;
    private Renderer renderer;
    private OrthographicCameraController cameraController;
    private Entity entity;
    private Entity primaryCamera;
    private Entity secondaryCamera;
    private boolean primary = true;
    private FrameBuffer frameBuffer;

    public void onAttach() {
        cameraController = new OrthographicCameraController(16.0f / 9.0f, true);
        renderer = RendererFactory.create();
        scene = new Scene(renderer);

        entity = scene.createEntity();
        entity.addComponent(new SpriteRenderComponent());

        primaryCamera = scene.createEntity();
        var primarySceneCamera = primaryCamera.addComponent(new SceneCameraComponent());
        primarySceneCamera.setPrimary(primary);

        secondaryCamera = scene.createEntity();
        var sceneCamera = secondaryCamera.addComponent(new SceneCameraComponent());
        sceneCamera.setPrimary(!primary);

        secondaryCamera.addComponent(new ScriptComponent()).bind(CameraController.class);

        scene.activateEntities(entity, primaryCamera, secondaryCamera);

        var spec = new FrameBufferSpecification(Application.getWindow().getWidth(), Application.getWindow().getHeight());
        frameBuffer = FrameBufferFactory.create(spec);
    }

    public void onDetach() {
        scene.dispose();
        frameBuffer.dispose();
    }

    public void onUpdate(float deltaTime) {
//        cameraController.onUpdate(deltaTime);

        // Resize
        FrameBufferSpecification spec = frameBuffer.getSpecification();
        if (viewportSize.x > 0 && viewportSize.y > 0 &&
                (viewportSize.x != spec.getWidth() || viewportSize.y != spec.getHeight())) {
            frameBuffer.resize((int) viewportSize.x, (int) viewportSize.y);
            scene.onViewportResize((int) viewportSize.x, (int) viewportSize.y);
        }

        // Render
        frameBuffer.bind();
        renderer.resetStats();
        renderer.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        renderer.clear();

        // Update
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

            {
                ImGui.text(entity.getComponent(TagComponent.class).getTag());
                if (ImGui.checkbox(primary ? "Primary" : "Secondary", primary)) {
                    primary = !primary;
                    primaryCamera.getComponent(SceneCameraComponent.class).setPrimary(primary);
                    secondaryCamera.getComponent(SceneCameraComponent.class).setPrimary(!primary);
                }

                ImGui.dragFloat3("Camera Position", cameraPosition);
                var transformComponent = primaryCamera.getComponent(TransformComponent.class);
                transformComponent.setTransform(new Matrix4f().translate(new Vector3f(cameraPosition), new Matrix4f()));

                {
                    var cameraComponent = secondaryCamera.getComponent(SceneCameraComponent.class);
                    if (ImGui.dragFloat("Zoom", orthographicSize)) {
                        cameraComponent.getCamera().setOrthographicSize(orthographicSize[0]);
                    }
                }
            }

        }
        ImGui.end();

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
        ImGui.begin("Viewport");
        {
            var viewportSize = ImGui.getContentRegionAvail();
            if (viewportSize.x != this.viewportSize.x || viewportSize.y != this.viewportSize.y) {
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

    public void onEvent(Event event) {
        cameraController.onEvent(event);
    }

    public static class CameraController extends ScriptableEntity {

        @Override
        public void onCreate() {
            System.out.println("CameraController created");
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public void onUpdate(float deltaTime) {
            var transformComponent = getComponent(TransformComponent.class);
            var speed = 5.0f * deltaTime;

            if (Input.isKeyPressed(NV_KEY_A)) {
                transformComponent.translate(-speed, 0);
            } else if (Input.isKeyPressed(NV_KEY_D)) {
                transformComponent.translate(speed, 0);
            }

            if (Input.isKeyPressed(NV_KEY_W)) {
                transformComponent.translate(0, speed);
            } else if (Input.isKeyPressed(NV_KEY_S)) {
                transformComponent.translate(0, -speed);
            }
        }
    }
}