package panels.properties;

import imgui.ImGui;
import imgui.type.ImInt;
import imgui.type.ImString;
import io.nova.core.renderer.texture.SubTexture;
import io.nova.ecs.component.AnimationClip;
import io.nova.ecs.component.AnimationComponent;
import io.nova.ecs.entity.Entity;
import panels.DragAndDropDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnimationComponentNode {

    private static boolean showAnimatorWindow = false;
    private static List<SubTexture> animationFrames = new ArrayList<>();
    private static ImString name = new ImString("Animation clip");
    private static ImInt samples = new ImInt(12);
    private static boolean edit = false;

    public static void create(Entity entity) {
        ComponentNode.create(entity, "Animation", AnimationComponent.class, () -> {
            var animation = entity.getComponent(AnimationComponent.class);

            if (ImGui.button("Create Animation Clip")) {
                showAnimatorWindow = true;
            }
            var currentClip = Objects.nonNull(animation.getCurrentClip()) ? animation.getCurrentClip().getName() : "None";
            if (ImGui.beginCombo("Animation Clips", currentClip)) {
                for (var clip : animation.getClips()) {
                    if (ImGui.selectable(clip.getName())) {
                        animation.setCurrentClip(clip);
                    }
                }
                ImGui.endCombo();
            }

            if (Objects.nonNull(animation.getCurrentClip()) && ImGui.button("Edit current")) {
                edit = true;
            }

            if (edit) {
                animatorWindow(animation.getCurrentClip());
            }

            if (showAnimatorWindow) {
                animatorWindow(animation);
            }
        });
    }

    private static void animatorWindow(AnimationComponent animation) {
        ImGui.begin("Animator");

        ImGui.inputInt("Samples", samples);

        ImGui.inputText("Name", name, 0);

        ImGui.dummy(100, 100);
        if (ImGui.beginDragDropTarget()) {
            var subTexturePayload = ImGui.acceptDragDropPayload(DragAndDropDataType.IMAGE_VIEWER_SUB_TEXTURE);
            if (Objects.nonNull(subTexturePayload)) {
                var subTexture = (SubTexture) subTexturePayload;
                animationFrames.add(subTexture);
            }
            ImGui.endDragDropTarget();
        }

        for (var frame : animationFrames) {
            var leftTop = frame.getLeftTop();
            var rightBottom = frame.getRightBottom();
            ImGui.image(frame.getTexture().getId(), 100, 100, leftTop[0], leftTop[1], rightBottom[0], rightBottom[1]);
            ImGui.sameLine();
        }

        if (ImGui.button("Create")) {
            var clip = new AnimationClip();
            clip.setName(name.get());
            clip.setSamples(samples.get());
            for (var frame : animationFrames) {
                clip.addFrame(frame);
            }
            animation.addClip(clip);
            showAnimatorWindow = false;
            animationFrames.clear();
            name.set("Animation clip");
            samples.set(12);
        }

        ImGui.end();
    }

    private static void animatorWindow(AnimationClip clip) {
        ImGui.begin("Animator");

        ImGui.inputInt("Samples", samples);

        ImGui.inputText("Name", name, 0);

        ImGui.dummy(100, 100);
        if (ImGui.beginDragDropTarget()) {
            var subTexturePayload = ImGui.acceptDragDropPayload(DragAndDropDataType.IMAGE_VIEWER_SUB_TEXTURE);
            if (Objects.nonNull(subTexturePayload)) {
                var subTexture = (SubTexture) subTexturePayload;
                clip.addFrame(subTexture);
            }
            ImGui.endDragDropTarget();
        }

        for (var frame : clip.getFrames()) {
            var leftTop = frame.getLeftTop();
            var rightBottom = frame.getRightBottom();
            ImGui.image(frame.getTexture().getId(), 100, 100, leftTop[0], leftTop[1], rightBottom[0], rightBottom[1]);
            ImGui.sameLine();
        }

        if (ImGui.button("Create")) {
            clip.setName(name.get());
            clip.setSamples(samples.get());
            edit = false;
        }

        ImGui.end();
    }
}