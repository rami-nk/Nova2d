package panels;

import imgui.ImGui;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContentBrowserPanel {

    private final String resourceRootDirectory;
    private final Map<File, File[]> directoryContentCache;
    private File currentDirectory;
    private int validCacheTimeout = 50;

    public ContentBrowserPanel() {
        var userDir = System.getProperty("user.dir");
        var path = Path.of(userDir, "SuperNova", "src", "main", "resources");
        currentDirectory = new File(path.toUri());
        resourceRootDirectory = path.toString();
        directoryContentCache = new HashMap<>();
    }

    public void onImGuiRender() {
        ImGui.begin("Content browser");

        boolean isRoot = currentDirectory.getAbsolutePath().equals(resourceRootDirectory);
        if (!isRoot && ImGui.button("<-")) {
            currentDirectory = currentDirectory.getParentFile();
        }
        if (validCacheTimeout != 0 && directoryContentCache.containsKey(currentDirectory)) {
            createDirectoryContent(directoryContentCache.get(currentDirectory));
        } else {
            validCacheTimeout = 1000;
            directoryContentCache.put(currentDirectory, currentDirectory.listFiles());
            createDirectoryContent(currentDirectory.listFiles());
        }
        validCacheTimeout--;

        ImGui.end();
    }

    private void createDirectoryContent(File[] files) {
        if (Objects.nonNull(files)) {
            for (var file : files) {
                if (file.isDirectory()) {
                    if (ImGui.button(file.getName() + "##" + file.getName())) {
                        currentDirectory = file;
                    }
                } else if (!file.isHidden()) {
                    ImGui.text(file.getName());
                }
            }
        }
    }
}