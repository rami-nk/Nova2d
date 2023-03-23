package panels;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiMouseCursor;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureLibrary;
import io.nova.ecs.Scene;
import io.nova.ecs.SceneState;

import java.nio.file.Path;

public class Toolbar {
    private final Texture playButtonTexture;
    private final Texture stopButtonTexture;
    private Scene context;

    public Toolbar(Scene context) {
        this.context = context;
        this.playButtonTexture = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/play-button.png"));
        this.stopButtonTexture = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/stop-button.png"));
    }

    public void setContext(Scene context) {
        this.context = context;
    }

    public void onImGuiRender() {

        ImGui.begin("##itembar", ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.NoBringToFrontOnFocus);
        {
            float size = 32.0f;
            ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 1);
            ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0);
            ImGui.pushStyleColor(ImGuiCol.WindowBg, 0.1f, 0.1f, 0.1f, 1.0f);
            ImGui.setWindowSize(ImGui.getWindowWidth(), size);
            var texture = context.getState() == SceneState.RUNNING ? stopButtonTexture : playButtonTexture;
            ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.1f, 0.1f, 0.1f, 1);
            ImGui.setCursorPosX(ImGui.getWindowWidth() / 2.0f - (size / 2.0f));
            if (ImGui.imageButton(texture.getId(), size, size, 0, 1, 1, 0, 0)) {
                if (context.getState() == SceneState.RUNNING) {
                    context.stop();
                } else {
                    context.play();
                }
            }
            var itemHovered = ImGui.isItemHovered();
            ImGui.setMouseCursor(itemHovered ? ImGuiMouseCursor.Hand : ImGuiMouseCursor.Arrow);
            ImGui.popStyleColor(3);
            ImGui.popStyleVar(2);
        }
        ImGui.end();
    }
}