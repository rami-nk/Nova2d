package panels;

import imgui.ImGui;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

public class ContentBrowserPanel {

    private File currentDirectory;

    public ContentBrowserPanel() {
        var userDir = System.getProperty("user.dir");
        var path = Path.of(userDir, "SuperNova", "src", "main", "resources");
        currentDirectory = new File(path.toUri());
    }

    public void onImGuiRender() {
        ImGui.begin("Content browser");

        createDirectoryContent(currentDirectory);

        ImGui.end();
    }

    private void createDirectoryContent(File currentFile) {
        if (ImGui.button("<-")) {
            currentDirectory = currentFile.getParentFile();
        }

        var files = currentFile.listFiles();
        if (Objects.nonNull(files)) {
            for (var file : files) {
                if (file.isDirectory()) {
                    if (ImGui.button(file.getName() + "##" + file.getName())) {
                        currentDirectory = file;
                    }
                } else {
                    ImGui.text(file.getName());
                }
            }
        }
    }
}