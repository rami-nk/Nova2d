package panels.properties;

import imgui.ImGui;
import imgui.type.ImString;
import io.nova.ecs.component.*;
import io.nova.ecs.entity.Entity;

public class TagComponentNode {
    public static void create(Entity entity) {
        if (entity.hasComponent(TagComponent.class)) {
            var tag = entity.getComponent(TagComponent.class);
            var string = new ImString(tag.getTag(), ImString.DEFAULT_LENGTH);
            if (ImGui.inputText("##Tag", string)) {
                tag.setTag(string.get());
            }

            ImGui.sameLine();

            ImGui.pushItemWidth(-1);
            if (ImGui.button("Add component")) {
                ImGui.openPopup("AddComponent");
            }
            ImGui.popItemWidth();

            if (ImGui.beginPopup("AddComponent")) {
                addComponentMenuItem(SceneCameraComponent.class, "Camera", entity);
                addComponentMenuItem(SpriteRenderComponent.class, "Sprite", entity);
                addComponentMenuItem(BoxColliderComponent.class, "BoxCollider", entity);
                addComponentMenuItem(RigidBodyComponent.class, "RigidBody", entity);
                ImGui.endPopup();
            }
        }
    }

    private static void addComponentMenuItem(Class<?> componentClass, String name, Entity entity) {
        if (!entity.hasComponent(componentClass) && ImGui.menuItem(name)) {
            try {
                entity.addComponent((Component) componentClass.getConstructor().newInstance());
                entity.getRegistry().updateEntity(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}