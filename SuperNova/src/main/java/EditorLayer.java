import imgui.ImGui;
import imgui.extension.imguizmo.ImGuizmo;
import imgui.extension.imguizmo.flag.Mode;
import imgui.extension.imguizmo.flag.Operation;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import io.nova.core.application.Application;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.*;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.ecs.Scene;
import io.nova.ecs.component.SceneCameraComponent;
import io.nova.ecs.component.ScriptComponent;
import io.nova.ecs.component.SpriteRenderComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.serializer.SceneSerializer;
import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.key.KeyPressedEvent;
import io.nova.utils.FileDialog;
import io.nova.window.Input;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import panels.EntityPanel;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Objects;

import static io.nova.core.codes.KeyCodes.*;

public class EditorLayer extends Layer {
    private final Vector2f viewportSize = new Vector2f();
    private Scene scene;
    private Renderer renderer;
    private OrthographicCameraController cameraController;
    private FrameBuffer frameBuffer;
    private EntityPanel entityPanel;
    private SceneSerializer sceneSerializer;
    private String filePath;

    public void onAttach() {
        cameraController = new OrthographicCameraController(16.0f / 9.0f, true);
        renderer = RendererFactory.create();
        sceneSerializer = new SceneSerializer();
        filePath = "assets/scenes/Cube.nova";

        try {
            scene = sceneSerializer.deserialize(filePath);
            scene.setRenderer(renderer);
        } catch (IOException e) {
            System.err.println("Failed to load scene: " + e.getMessage());
            System.err.println("Created default scene instead.");

            scene = new Scene(renderer);

            Entity entity = scene.createEntity("Quad");
            entity.addComponent(new SpriteRenderComponent());

            Entity entity2 = scene.createEntity("Quad2");
            entity2.addComponent(new SpriteRenderComponent());

            Entity primaryCamera = scene.createEntity("Primary Camera");
            primaryCamera.addComponent(new SceneCameraComponent()).setPrimary(true);

            Entity secondaryCamera = scene.createEntity("Secondary Camera");
            secondaryCamera.addComponent(new SceneCameraComponent());

            secondaryCamera.addComponent(new ScriptComponent()).bind(CameraController.class);

            scene.activateEntities(entity, entity2, primaryCamera, secondaryCamera);
        }

        var spec = new FrameBufferSpecification(Application.getWindow().getWidth(), Application.getWindow().getHeight());
        frameBuffer = FrameBufferFactory.create(spec);

        entityPanel = new EntityPanel(scene);
    }

    public void onDetach() {
        scene.dispose();
        frameBuffer.dispose();
    }

    public void onUpdate(float deltaTime) {
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

        // Dockspace
        var style = ImGui.getStyle();
        var minWindowSize = style.getWindowMinSize();
        style.setWindowMinSize(350.0f, minWindowSize.y);
        ImGui.dockSpace(ImGui.getID("Dockspace"));
        style.setWindowMinSize(minWindowSize.x, minWindowSize.y);

        // Menubar
        ImGui.beginMenuBar();
        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("New", "Ctrl+N")) {
                newScene();
            }
            if (ImGui.menuItem("Open...", "Ctrl+O")) {
                openScene();
            }
            if (ImGui.menuItem("Save", "Ctrl+S")) {
                saveFile();
            }
            if (ImGui.menuItem("Save as...", "Ctrl+Shift+S")) {
                saveFileAs();
            }
            if (ImGui.menuItem("Exit", "Ctrl+Q")) {
                closeApplication();
            }
            ImGui.endMenu();
        }
        ImGui.endMenuBar();

        entityPanel.onImGuiRender();

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
                this.viewportSize.x = viewportSize.x;
                this.viewportSize.y = viewportSize.y;
            }

            var textureId = frameBuffer.getColorAttachmentRendererId();
            ImGui.image(textureId, viewportSize.x, viewportSize.y, 0, 1, 1, 0);
            // We set the viewPortSize of the cameraController here to make sure the aspect ratio is correct after resizing the window
            cameraController.setViewportSize((int) viewportSize.x, (int) viewportSize.y);
        }

        // ImGuizmo
        {
            var selectedEntity = entityPanel.getSelectedEntity();
            var cameraEntity = scene.getPrimaryCameraEntity();
            if (!Objects.isNull(selectedEntity) && !selectedEntity.equals(cameraEntity)) {
                ImGuizmo.setOrthographic(false);
                ImGuizmo.setDrawList();
                ImGuizmo.setRect(ImGui.getWindowPosX(), ImGui.getWindowPosY(), ImGui.getWindowWidth(), ImGui.getWindowHeight());

                if (!Objects.isNull(cameraEntity)) {
                    var camera = cameraEntity.getComponent(SceneCameraComponent.class).getCamera();
                    var transform = cameraEntity.getComponent(TransformComponent.class).getTransform();
                    var cameraProjection = camera.getProjection();
                    var cameraView = transform.invert(new Matrix4f());

                    var selectedEntityTransformComponent = selectedEntity.getComponent(TransformComponent.class);
                    var entityTransform = selectedEntityTransformComponent.getTransform();

                    var cameraViewArr = cameraView.get(new float[16]);
                    var cameraProjectionArr = cameraProjection.get(new float[16]);
                    var entityTransformArr = entityTransform.get(new float[16]);
                    ImGuizmo.manipulate(
                            cameraViewArr,
                            cameraProjectionArr,
                            entityTransformArr,
                            Operation.TRANSLATE,
                            Mode.LOCAL
                    );

                    if (ImGuizmo.isUsing()) {
                        var model = new Matrix4f(FloatBuffer.wrap(entityTransformArr));

                        var translation = new float[]{model.m30(), model.m31(), model.m32()};

                        selectedEntityTransformComponent.setTranslation(translation);
                    }
                }
            }
        }
        ImGui.end();
        ImGui.popStyleVar();

        ImGui.end();
    }

    public void onEvent(Event event) {
        cameraController.onEvent(event);

        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(KeyPressedEvent.class, this::handleShortcuts);
    }

    private boolean handleShortcuts(KeyPressedEvent event) {
        if (event.isRepeat()) return false;

        var control = Input.isKeyPressed(NV_KEY_LEFT_CONTROL) || Input.isKeyPressed(NV_KEY_RIGHT_CONTROL);
        var shift = Input.isKeyPressed(NV_KEY_LEFT_SHIFT) || Input.isKeyPressed(NV_KEY_RIGHT_SHIFT);
        switch (event.getKeyCode()) {
            case NV_KEY_S -> {
                if (control && shift) {
                    saveFileAs();
                    return true;
                }
                if (control) {
                    saveFile();
                }
            }
            case NV_KEY_N -> {
                if (control) {
                    newScene();
                }
            }
            case NV_KEY_O -> {
                if (control) {
                    openScene();
                }
            }
            case NV_KEY_Q -> {
                if (control) {
                    closeApplication();
                }
            }
        }
        return true;
    }

    private void openScene() {
        var filePath = FileDialog.openFileDialog("Nova files (*.nova)\0*.nova\0");
        if (!Objects.isNull(filePath)) {
            try {
                scene = sceneSerializer.deserialize(filePath);
                scene.setRenderer(renderer);
                entityPanel = new EntityPanel(scene);
                scene.onViewportResize((int) viewportSize.x, (int) viewportSize.y);
                this.filePath = filePath;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void newScene() {
        scene = new Scene(renderer);
        sceneSerializer = new SceneSerializer();
        entityPanel = new EntityPanel(scene);
        filePath = null;
    }

    private void saveFileAs() {
        var filePath = FileDialog.saveFileDialog("Nova files (*.nova)\0*.nova\0");
        if (!Objects.isNull(filePath)) {
            try {
                sceneSerializer.serialize(scene, filePath);
                this.filePath = filePath;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveFile() {
        if (!Objects.isNull(filePath)) {
            try {
                sceneSerializer.serialize(scene, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            var filePath = FileDialog.saveFileDialog("Nova files (*.nova)\0*.nova\0");
            if (!Objects.isNull(filePath)) {
                try {
                    sceneSerializer.serialize(scene, filePath);
                    this.filePath = filePath;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void closeApplication() {
        Application.getInstance().close();
    }
}