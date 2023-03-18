package panels;

import imgui.ImGui;

import java.io.File;
import java.util.Objects;

public class ContentBrowserPanel {

    public void onImGuiRender() {
        ImGui.begin("Content browser");

        var currentDirectory = new File(System.getProperty("user.dir"));
        var files = currentDirectory.listFiles();

        if (Objects.nonNull(files)) {
            for (var file : files) {
                ImGui.text(file.getName());
            }
        }

        ImGui.end();
    }
}