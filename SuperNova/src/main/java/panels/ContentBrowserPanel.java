package panels;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiMouseButton;
import io.nova.core.renderer.texture.TextureLibrary;

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
    private float padding = 16.0f;
    private float iconSize = 64.0f;
    private float cellSize = iconSize + padding;
    private String draggedContent = "";

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
            var panelWidth = ImGui.getContentRegionAvail().x;
            int columns = (int) (panelWidth / cellSize);
            columns = columns == 0 ? 5 : (columns * (cellSize + 1) <= panelWidth ? columns : columns - 1);
            ImGui.columns(columns, "content_browser", false);
            for (var file : files) {
                if (file.isDirectory()) {
                    var texture = TextureLibrary.getOrElseUploadTexture("directory.png");
                    ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
                    ImGui.pushID(file.getName());
                    ImGui.imageButton(texture.getId(), iconSize, iconSize, 0, 1, 1, 0);

                    if (ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(ImGuiMouseButton.Left)) {
                        currentDirectory = file;
                    }
                    ImGui.popID();
                    ImGui.popStyleColor();

                    ImGui.textWrapped(file.getName());
                    ImGui.nextColumn();
                } else if (!file.isHidden()) {
                    var texture = TextureLibrary.getOrElseUploadTexture("document.png");
                    ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
                    ImGui.pushID(file.getName());

                    ImGui.imageButton(texture.getId(), iconSize, iconSize, 0, 1, 1, 0);
                    if (ImGui.beginDragDropSource()) {
                        ImGui.setDragDropPayload(DragAndDropDataType.CONTENT_BROWSER_ITEM, file.getAbsolutePath());
                        ImGui.image(texture.getId(), iconSize, iconSize, 0, 1, 1, 0);
                        ImGui.text(file.getName());
                        ImGui.endDragDropSource();
                    }
                    ImGui.textWrapped(file.getName());

                    ImGui.popStyleColor();
                    ImGui.popID();
                    ImGui.nextColumn();
                }
            }
            ImGui.columns();
        }
    }
}