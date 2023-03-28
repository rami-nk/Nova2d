package panels;

import imgui.ImGui;
import imgui.flag.ImGuiPopupFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import io.nova.core.application.Application;
import io.nova.core.renderer.Renderer;
import io.nova.core.renderer.RendererFactory;
import io.nova.core.renderer.framebuffer.*;
import io.nova.ecs.Scene;
import io.nova.ecs.component.SceneCameraComponent;
import io.nova.ecs.component.TagComponent;
import io.nova.ecs.component.TransformComponent;
import io.nova.ecs.entity.Entity;
import panels.properties.EntityPropertiesPanel;

import java.util.Objects;

public class EntityPanel {

    private Scene scene;
    private Entity selectedEntity;
    private boolean removeEntityClicked = false;
    private Entity entityToRemove;
    private boolean showCameraPreview;
    private FrameBuffer cameraPreviewFramebuffer;
    private Renderer renderer;

    public EntityPanel(Scene scene) {
        this.scene = scene;

        var spec = new FrameBufferSpecification(Application.getWindow().getWidth(), Application.getWindow().getHeight());
        spec.setAttachments(
                new FrameBufferTextureSpecification(FrameBufferTextureFormat.RGBA8),
                new FrameBufferTextureSpecification(FrameBufferTextureFormat.DEPTH24STENCIL8)
        );
        this.cameraPreviewFramebuffer = FrameBufferFactory.create(spec);
        this.renderer = RendererFactory.create();
    }

    public void setContext(Scene scene) {
        this.scene = scene;
    }

    public void onImGuiRender() {
        ImGui.begin("Entities");
        {
            for (var entity : scene.getRegistry().getEntities()) {
                createEntityNode(entity);
            }
            if (ImGui.isMouseDown(0) && ImGui.isWindowHovered()) {
//                selectedEntity = null;
            }
            createEntityPopupMenu();
        }
        ImGui.end();

        if (removeEntityClicked) {
            scene.removeEntity(entityToRemove);
            removeEntityClicked = false;
            if (Objects.equals(selectedEntity, entityToRemove)) {
                selectedEntity = null;
                showCameraPreview = false;
            }
        }

        if (Objects.nonNull(selectedEntity) && showCameraPreview) {
            var openFlag = new ImBoolean(true);
            ImGui.begin("Camera Preview", openFlag, ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoScrollWithMouse);
            {
                var camera = selectedEntity.getComponent(SceneCameraComponent.class).getCamera();
                var transform = selectedEntity.getComponent(TransformComponent.class).getTransform();
                cameraPreviewFramebuffer.bind();
                renderer.setClearColor(0.1f, 0.1f, 0.1f, 1.0f);
                renderer.clear();
                scene.getCameraView(camera, transform);
                cameraPreviewFramebuffer.unbind();

                var textureId = cameraPreviewFramebuffer.getColorAttachmentRendererId();
                ImGui.image(textureId, ImGui.getWindowWidth(), ImGui.getWindowHeight(), 0, 1, 1, 0);
            }
            ImGui.end();
            showCameraPreview = openFlag.get();
        }

        ImGui.begin("Properties");
        {
            if (Objects.nonNull(selectedEntity)) {
                EntityPropertiesPanel.create(selectedEntity);
            }
        }
        ImGui.end();
    }

    private void createEntityPopupMenu() {
        if (ImGui.beginPopupContextWindow("NewEntity", ImGuiPopupFlags.NoOpenOverItems | ImGuiPopupFlags.MouseButtonRight)) {
            if (ImGui.menuItem("New Entity")) {
                var entity = scene.createEntity();
                scene.activateEntities(entity);
                selectedEntity = entity;
            }
            ImGui.endPopup();
        }
    }

    private void createEntityNode(Entity entity) {
        String tag = entity.getComponent(TagComponent.class).getTag();
        var flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.SpanAvailWidth;
        if (selectedEntity == entity) {
            flags |= ImGuiTreeNodeFlags.Selected;
        }
        // push ID to avoid crash when ID is empty while editing
        ImGui.pushID(entity.hashCode());
        boolean expanded = ImGui.treeNodeEx(tag, flags);
        ImGui.popID();
        if (ImGui.isItemClicked()) {
            selectedEntity = entity;
            showCameraPreview = selectedEntity.hasComponent(SceneCameraComponent.class);
        }

        removeEntityPopupMenu(entity);

        if (expanded) {
            ImGui.treePop();
        }
    }

    private void removeEntityPopupMenu(Entity entity) {
        if (ImGui.beginPopupContextItem()) {
            if (ImGui.menuItem("Delete Entity")) {
                // Defer removal to avoid concurrent modification exception
                removeEntityClicked = true;
                entityToRemove = entity;
            }
            ImGui.endPopup();
        }
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(Entity entity) {
        selectedEntity = entity;
    }
}