import imgui.ImGui;
import imgui.extension.imguizmo.ImGuizmo;
import imgui.extension.imguizmo.flag.Mode;
import imgui.extension.imguizmo.flag.Operation;
import imgui.flag.*;
import io.nova.core.application.Application;
import io.nova.core.codes.KeyCode;
import io.nova.core.codes.MouseCode;
import io.nova.core.layer.Layer;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.RendererFactory;
import io.nova.core.renderer.camera.EditorCamera;
import io.nova.core.renderer.camera.OrthographicCameraController;
import io.nova.core.renderer.framebuffer.*;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureLibrary;
import io.nova.ecs.Scene;
import io.nova.ecs.SceneState;
import io.nova.ecs.component.*;
import io.nova.ecs.entity.Entity;
import io.nova.ecs.entity.Group;
import io.nova.ecs.serializer.SceneSerializer;
import io.nova.event.Event;
import io.nova.event.EventDispatcher;
import io.nova.event.key.KeyPressedEvent;
import io.nova.event.mouse.MouseButtonPressedEvent;
import io.nova.utils.FileDialog;
import io.nova.window.Input;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import panels.ContentBrowserPanel;
import panels.DragAndDropDataType;
import panels.EntityPanel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

import static imgui.extension.imguizmo.flag.Operation.*;

public class EditorLayer extends Layer {
    private final Vector2f viewportSize = new Vector2f();
    private Scene activeScene, editorScene, runtimeScene;
    private Renderer renderer;
    private OrthographicCameraController cameraController;
    private FrameBuffer frameBuffer;
    private EntityPanel entityPanel;
    private ContentBrowserPanel contentBrowserPanel;
    private SceneSerializer sceneSerializer;
    private String filePath;
    private int gizmoOperation;
    private EditorCamera editorCamera;
    private Vector2f[] viewportBounds = new Vector2f[2];
    private Entity hoveredEntity;
    private boolean viewportHovered = false;
    private boolean viewportFocused = false;
    private Entity entityCopy;
    private SceneState sceneState;
    private Texture playButtonTexture, stopButtonTexture;
    private boolean showPhysicsColliders;

    public void onAttach() {
        cameraController = new OrthographicCameraController(16.0f / 9.0f, true);
        renderer = RendererFactory.create();
        sceneSerializer = Scene.serializer;
        filePath = "assets/scenes/Cube.nova";

        try {
            editorScene = sceneSerializer.deserialize(filePath);
            editorScene.setRenderer(renderer);
            activeScene = editorScene;
        } catch (IOException e) {
            System.err.println("Failed to load scene: " + e.getMessage());
            System.err.println("Created default scene instead.");

            activeScene = new Scene(renderer);

            Entity entity = activeScene.createEntity("Quad");
            entity.addComponent(new SpriteRendererComponent());

            Entity entity2 = activeScene.createEntity("Quad2");
            entity2.addComponent(new SpriteRendererComponent());

            Entity primaryCamera = activeScene.createEntity("Primary Camera");
            primaryCamera.addComponent(new SceneCameraComponent()).setPrimary(true);

            Entity secondaryCamera = activeScene.createEntity("Secondary Camera");
            secondaryCamera.addComponent(new SceneCameraComponent());

            secondaryCamera.addComponent(new ScriptComponent()).bind(CameraController.class);

            activeScene.activateEntities(entity, entity2, primaryCamera, secondaryCamera);
        }

        var spec = new FrameBufferSpecification(Application.getWindow().getWidth(), Application.getWindow().getHeight());
        spec.setAttachments(
                new FrameBufferTextureSpecification(FrameBufferTextureFormat.RGBA8),
                new FrameBufferTextureSpecification(FrameBufferTextureFormat.RED_INTEGER),
                new FrameBufferTextureSpecification(FrameBufferTextureFormat.DEPTH24STENCIL8)
        );
        frameBuffer = FrameBufferFactory.create(spec);

        entityPanel = new EntityPanel(activeScene);
        contentBrowserPanel = new ContentBrowserPanel();
        editorCamera = new EditorCamera(30.0f, 1.778f, 0.1f, 1000.0f);
        sceneState = SceneState.EDITING;
        this.playButtonTexture = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/play-button.png"));
        this.stopButtonTexture = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/stop-button.png"));
    }

    public void onDetach() {
        activeScene.dispose();
        frameBuffer.dispose();
        activeScene.dispose();
    }

    public void onUpdate(float deltaTime) {
        // Resize
        FrameBufferSpecification spec = frameBuffer.getSpecification();
        if (viewportSize.x > 0 && viewportSize.y > 0 &&
                (viewportSize.x != spec.getWidth() || viewportSize.y != spec.getHeight())) {
            frameBuffer.resize((int) viewportSize.x, (int) viewportSize.y);
            activeScene.onViewportResize((int) viewportSize.x, (int) viewportSize.y);
            editorCamera.setViewportSize(viewportSize.x, viewportSize.y);
        }

        // Update camera
        editorCamera.onUpdate(deltaTime);

        // Render
        frameBuffer.bind();
        renderer.resetStats();
        renderer.setClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        renderer.clear();

        frameBuffer.clearAttachment(1, -1);

        // Update
        switch (sceneState) {
            case EDITING -> activeScene.onUpdateEditor(editorCamera, deltaTime);
            case RUNNING -> activeScene.onUpdateRuntime(deltaTime);
        }

        // Mouse picking
        if (viewportBounds[0] != null) {
            var m = ImGui.getMousePos();
            m.x -= viewportBounds[0].x;
            m.y -= viewportBounds[0].y;

            var vs = viewportBounds[1].sub(viewportBounds[0], new Vector2f());

            m.y = viewportSize.y - m.y;

            int mouseX = (int) m.x;
            int mouseY = (int) m.y;

            if (mouseX >= 0 && mouseX < vs.x && mouseY >= 0 && mouseY < vs.y) {
                int id = frameBuffer.readPixel(1, mouseX, mouseY);
                if (id > 0) {
                    var entity = activeScene.getRegistry().getEntity(id);
                    if (entity != null) {
                        hoveredEntity = entity;
                    }
                } else {
                    hoveredEntity = null;
                }
            }
        }

        if (showPhysicsColliders) {
            drawPhysicsColliders();
        }

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
        contentBrowserPanel.onImGuiRender();
        createToolbar();

        ImGui.begin("Renderer stats");
        {
            var stats = renderer.getStats();
            ImGui.text("Draw calls: " + stats.getDrawCalls());
            ImGui.text("Quad count: " + stats.getQuadCount());
            ImGui.text("Vertex count: " + stats.getTotalVertexCount());
            ImGui.text("Index count: " + stats.getTotalIndexCount());
        }
        ImGui.end();

        ImGui.begin("Settings");
        {
            if (ImGui.checkbox("Show physics colliders", showPhysicsColliders)) {
                showPhysicsColliders = !showPhysicsColliders;
            }
        }
        ImGui.end();

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);
        ImGui.begin("Viewport");
        {
            var viewPortOffset = ImGui.getCursorPos();

            var viewportSize = ImGui.getContentRegionAvail();
            if (viewportSize.x != this.viewportSize.x || viewportSize.y != this.viewportSize.y) {
                this.viewportSize.x = viewportSize.x;
                this.viewportSize.y = viewportSize.y;
            }

            viewportHovered = ImGui.isWindowHovered();
            viewportFocused = ImGui.isWindowFocused();

            var windowSize = ImGui.getWindowSize();
            var minBound = ImGui.getWindowPos();
            minBound.x += viewPortOffset.x;
            minBound.y += viewPortOffset.y;

            var maxBound = new Vector2f(minBound.x + windowSize.x, minBound.y + windowSize.y);
            viewportBounds[0] = new Vector2f(minBound.x, minBound.y);
            viewportBounds[1] = maxBound;

            var textureId = frameBuffer.getColorAttachmentRendererId();
            ImGui.image(textureId, viewportSize.x, viewportSize.y, 0, 1, 1, 0);

            if (ImGui.beginDragDropTarget()) {
                var payload = ImGui.acceptDragDropPayload(DragAndDropDataType.CONTENT_BROWSER_ITEM);
                if (payload != null) {
                    var path = payload.toString();
                    openScene(path);
                }
                ImGui.endDragDropTarget();
            }

            // We set the viewPortSize of the cameraController here to make sure the aspect ratio is correct after resizing the window
            cameraController.setViewportSize((int) viewportSize.x, (int) viewportSize.y);
        }

        // ImGuizmo
        {
            var selectedEntity = entityPanel.getSelectedEntity();
            if (sceneState == SceneState.EDITING && gizmoOperation != -1 && !Objects.isNull(selectedEntity)) {
                ImGuizmo.setOrthographic(false);
                ImGuizmo.setDrawList();
                ImGuizmo.setRect(ImGui.getWindowPosX(), ImGui.getWindowPosY(), ImGui.getWindowWidth(), ImGui.getWindowHeight());

                // Runtime code, we will need it later
//                var camera = cameraEntity.getComponent(SceneCameraComponent.class).getCamera();
//                var transform = cameraEntity.getComponent(TransformComponent.class).getTransform();
//                var cameraProjection = camera.getProjection();
//                var cameraView = transform.invert(new Matrix4f());

                var cameraProjection = editorCamera.getProjection();
                var cameraView = editorCamera.getViewMatrix();

                var selectedEntityTransformComponent = selectedEntity.getComponent(TransformComponent.class);

                var model = new float[16];
                var radRot = selectedEntityTransformComponent.getRotation();
                var degRot = new float[3];
                for (int i = 0; i < radRot.length; i++) {
                    degRot[i] = (float) Math.toDegrees(radRot[i]);
                }

                ImGuizmo.recomposeMatrixFromComponents(
                        model,
                        selectedEntityTransformComponent.getTranslation(),
                        degRot,
                        selectedEntityTransformComponent.getScale());

                var canSnap = Input.isKeyPressed(KeyCode.KEY_LEFT_CONTROL);
                var snapValue = gizmoOperation == ROTATE ? 45.0f : 0.5f;
                var snapValues = new float[]{snapValue, snapValue, snapValue};
                ImGuizmo.manipulate(
                        cameraView.get(new float[16]),
                        cameraProjection.get(new float[16]),
                        model,
                        gizmoOperation,
                        Mode.LOCAL,
                        canSnap ? snapValues : new float[3]
                );

                if (ImGuizmo.isUsing()) {
                    var translation = new float[3];
                    var rotation = new float[3];
                    var scale = new float[3];

                    ImGuizmo.decomposeMatrixToComponents(model, translation, rotation, scale);

                    for (int i = 0; i < rotation.length; i++) {
                        rotation[i] = (float) Math.toRadians(rotation[i]);
                    }

                    switch (gizmoOperation) {
                        case TRANSLATE -> selectedEntityTransformComponent.setTranslation(translation);
                        case SCALE -> selectedEntityTransformComponent.setScale(scale);
                        case ROTATE -> selectedEntityTransformComponent.setRotation(rotation);
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
        // TODO: Make application block event function
        // Example: application.blockEvent(event, condition)
        if (viewportFocused && viewportHovered) {
            editorCamera.onEvent(event);
        }

        var dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(KeyPressedEvent.class, this::handleShortcuts);
        dispatcher.dispatch(MouseButtonPressedEvent.class, this::handleMouePicking);
    }

    private void drawPhysicsColliders() {

        switch (sceneState) {
            case EDITING -> renderer.beginScene(editorCamera);
            case RUNNING -> {
                var camera = activeScene.getPrimaryCameraEntity();
                var cameraComponent = camera.getComponent(SceneCameraComponent.class);
                var transform = camera.getComponent(TransformComponent.class);
                renderer.beginScene(cameraComponent.getCamera(), transform.getTransform());
            }
        }

        var colliderColor = new Vector4f(0.0f, 1.0f, 0.0f, 1.0f);
        var registry = activeScene.getRegistry();
        {
            var view = registry.getEntities(Group.create(BoxColliderComponent.class));
            for (var entity : view) {
                var collider = entity.getComponent(BoxColliderComponent.class);
                var transform = entity.getComponent(TransformComponent.class);

                var translation = transform.getTranslation();
                var position = new float[3];
                position[0] = translation[0] + collider.getOffset()[0];
                position[1] = translation[1] + collider.getOffset()[1];
                position[2] = 0.001f;

                var rotation = transform.getRotation()[2];

                var scale = transform.getScale();
                var size = new float[3];
                size[0] = scale[0] * collider.getSize()[0] * 2.0f;
                size[1] = scale[1] * collider.getSize()[1] * 2.0f;
                size[2] = 1.0f;

                var colliderTransform = new Matrix4f()
                        .translate(new Vector3f(position))
                        .rotate(rotation, new Vector3f(0.0f, 0.0f, 1.0f))
                        .scale(new Vector3f(size));

                renderer.drawRect(colliderTransform, colliderColor);
            }
        }
        {
            var view = registry.getEntities(Group.create(CircleColliderComponent.class));
            for (var entity : view) {
                var collider = entity.getComponent(CircleColliderComponent.class);
                var transform = entity.getComponent(TransformComponent.class);

                var position = new float[3];
                var translation = transform.getTranslation();
                position[0] = translation[0] + collider.getOffset()[0];
                position[1] = translation[1] + collider.getOffset()[1];
                position[2] = 0.001f;

                var scale = transform.getScale();
                var size = new float[3];
                size[0] = scale[0] * collider.getRadius() * 2.0f;
                size[1] = scale[1] * collider.getRadius() * 2.0f;
                size[2] = 1.0f;

                var colliderTransform = new Matrix4f()
                        .translate(new Vector3f(position))
                        .scale(new Vector3f(size));

                renderer.drawCircle(colliderTransform, colliderColor, 0.05f, 0.0f, -1);
            }
        }
        renderer.endScene();
    }

    public void createToolbar() {
        ImGui.begin("##itembar", ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoBringToFrontOnFocus);
        {
            float size = 32.0f;
            ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 1);
            ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0);
            ImGui.pushStyleColor(ImGuiCol.WindowBg, 0.1f, 0.1f, 0.1f, 1.0f);
            ImGui.setWindowSize(ImGui.getWindowWidth(), size);
            var texture = sceneState == SceneState.RUNNING ? stopButtonTexture : playButtonTexture;
            ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.1f, 0.1f, 0.1f, 1);
            ImGui.setCursorPosX(ImGui.getWindowWidth() / 2.0f - (size / 2.0f));
            if (ImGui.imageButton(texture.getId(), size, size, 0, 1, 1, 0, 0)) {
                if (sceneState == SceneState.RUNNING) {
                    stop();
                } else {
                    play();
                }
            }
            var itemHovered = ImGui.isItemHovered();
            ImGui.setMouseCursor(itemHovered ? ImGuiMouseCursor.Hand : ImGuiMouseCursor.Arrow);
            ImGui.popStyleColor(3);
            ImGui.popStyleVar(2);
        }
        ImGui.end();
    }

    private void play() {
        runtimeScene = activeScene.copy();
        activeScene = runtimeScene;
        activeScene.setRenderer(renderer);
        entityPanel.setContext(activeScene);
        activeScene.onRuntimeStart();
        sceneState = SceneState.RUNNING;
    }

    private void stop() {
        activeScene.onRuntimeStop();
        runtimeScene = null;
        activeScene = editorScene;
        entityPanel.setContext(activeScene);
        sceneState = SceneState.EDITING;
    }

    private boolean handleShortcuts(KeyPressedEvent event) {
        if (event.isRepeat()) return false;

        var control = Input.isKeyPressed(KeyCode.KEY_LEFT_CONTROL) || Input.isKeyPressed(KeyCode.KEY_RIGHT_CONTROL);
        var shift = Input.isKeyPressed(KeyCode.KEY_LEFT_SHIFT) || Input.isKeyPressed(KeyCode.KEY_RIGHT_SHIFT);
        var cmd = Input.isKeyPressed(KeyCode.KEY_LEFT_SUPER) || Input.isKeyPressed(KeyCode.KEY_RIGHT_SUPER);
        switch (event.getKeyCode()) {
            case KEY_S -> {
                if (control && shift) {
                    saveFileAs();
                    return true;
                }
                if (control) {
                    saveFile();
                }
            }
            case KEY_N -> {
                if (control) {
                    newScene();
                }
            }
            case KEY_O -> {
                if (control) {
                    openScene();
                }
            }
            case KEY_Q -> {
                if (control) {
                    closeApplication();
                } else {
                    gizmoOperation = -1;
                }
            }
            case KEY_W -> gizmoOperation = Operation.TRANSLATE;
            case KEY_E -> gizmoOperation = Operation.ROTATE;
            case KEY_R -> gizmoOperation = Operation.SCALE;

            // Copy and paste entities
            case KEY_C -> {
                if (cmd) {
                    var selectedEntity = entityPanel.getSelectedEntity();
                    if (!Objects.isNull(selectedEntity)) {
                        var entity = activeScene.createVoidEntity();
                        for (var component : selectedEntity.getComponents()) {
                            var componentCopy = component.copy(component.getClass());
                            entity.addComponent(componentCopy);
                        }
                        entityCopy = entity;
                    }
                }
            }
            case KEY_V -> {
                if (cmd && !Objects.isNull(entityCopy)) {
                    activeScene.activateEntities(entityCopy);
                    entityCopy.getRegistry().updateEntity(entityCopy);
                    entityPanel.setSelectedEntity(entityCopy);
                    entityCopy = null;
                }
            }
        }
        return true;
    }

    private void openScene() {
        var filePath = FileDialog.openFileDialog("Nova files (*.nova)\0*.nova\0");
        openScene(filePath);
    }

    private void openScene(String path) {
        if (sceneState == SceneState.RUNNING) {
            stop();
        }
        Function<String, Boolean> isNovaFile = (String p) -> Path.of(p).getFileName().toString().split("\\.")[1].equals("nova");
        if (!Objects.isNull(path) && isNovaFile.apply(path)) {
            try {
                editorScene = sceneSerializer.deserialize(path);
                editorScene.setRenderer(renderer);
                entityPanel.setContext(editorScene);
                editorScene.onViewportResize((int) viewportSize.x, (int) viewportSize.y);
                activeScene = editorScene;
                this.filePath = path;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void newScene() {
        editorScene = new Scene(renderer);
        entityPanel.setContext(editorScene);
        filePath = null;
        activeScene = editorScene;
    }

    private void saveFileAs() {
        var filePath = FileDialog.saveFileDialog("Nova files (*.nova)\0*.nova\0");
        if (!Objects.isNull(filePath)) {
            try {
                sceneSerializer.serialize(activeScene, filePath);
                this.filePath = filePath;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveFile() {
        if (!Objects.isNull(filePath)) {
            try {
                sceneSerializer.serialize(activeScene, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            var filePath = FileDialog.saveFileDialog("Nova files (*.nova)\0*.nova\0");
            if (!Objects.isNull(filePath)) {
                try {
                    sceneSerializer.serialize(activeScene, filePath);
                    this.filePath = filePath;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean handleMouePicking(MouseButtonPressedEvent event) {
        if (event.getMouseCode() == MouseCode.BUTTON_LEFT) {
            boolean notUsingGizmos = !ImGuizmo.isUsing() && !ImGuizmo.isOver();
            if (notUsingGizmos && viewportHovered && !editorCamera.isMoving()) {
                entityPanel.setSelectedEntity(hoveredEntity);
            }
        }
        return false;
    }

    private void closeApplication() {
        Application.getInstance().close();
    }
}