package panels;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureLibrary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContentBrowserPanel {

    private final String resourceRootDirectory;
    private final Map<File, File[]> directoryContentCache;
    private final Texture directoryIconTexture, documentIconTexture, returnIconTexture;
    private File currentDirectory;
    private int validCacheTimeout = 50;
    private float padding = 16.0f;
    private float iconSize = 64.0f;
    private float cellSize = iconSize + padding;

    public ContentBrowserPanel() {
        var userDir = System.getProperty("user.dir");
        var path = Path.of(userDir, "SuperNova", "src", "main", "resources");
        currentDirectory = new File(path.toUri());
        resourceRootDirectory = path.toString();
        directoryContentCache = new HashMap<>();
        directoryIconTexture = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/directory.png"));
        documentIconTexture = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/document.png"));
        returnIconTexture = TextureLibrary.uploadAndGet(Path.of("SuperNova/src/main/resources/icons/back-arrow.png"));
    }

    private static void sortByDirectoriesFirst(File[] files) {
        Arrays.sort(files, (f1, f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            } else if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            } else {
                return f1.getName().compareTo(f2.getName());
            }
        });
    }

    public void onImGuiRender() {
        ImGui.pushStyleVar(ImGuiStyleVar.ScrollbarSize, 10.0f);
        ImGui.begin("Content browser", ImGuiWindowFlags.AlwaysVerticalScrollbar);

        boolean isRoot = currentDirectory.getAbsolutePath().equals(resourceRootDirectory);

        ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
        var returnIconSize = 32.0f;
        boolean isReturnClicked = ImGui.imageButton(returnIconTexture.getId(), returnIconSize, returnIconSize, 0, 1, 1, 0);
        ImGui.popStyleColor();

        ImGui.sameLine();
        var textSize = new ImVec2();
        ImGui.calcTextSize(textSize, currentDirectory.getName());
        ImGui.setCursorPosY(ImGui.getCursorPosY() + (returnIconSize / 2.0f) - (textSize.y / 2.0f));
        ImGui.text(currentDirectory.getName());

        if (!isRoot && isReturnClicked) {
            currentDirectory = currentDirectory.getParentFile();
        }
        if (validCacheTimeout != 0 && directoryContentCache.containsKey(currentDirectory)) {
            createDirectoryContent(directoryContentCache.get(currentDirectory));
        } else {
            validCacheTimeout = 100;
            var files = currentDirectory.listFiles();
            sortByDirectoriesFirst(files);
            directoryContentCache.put(currentDirectory, files);
            createDirectoryContent(files);
        }
        validCacheTimeout--;

        ImGui.end();
        ImGui.popStyleVar();
    }

    private void createDirectoryContent(File[] files) {
        if (Objects.nonNull(files)) {
            var panelWidth = ImGui.getContentRegionAvailX();
            int columns = (int) (panelWidth / cellSize);
            columns = columns == 0 ? 1 : columns;
            ImGui.columns(columns, "content_browser", false);
            for (var file : files) {
                if (file.isDirectory()) {
                    ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
                    ImGui.pushID(file.getName());
                    ImGui.imageButton(directoryIconTexture.getId(), iconSize, iconSize, 0, 1, 1, 0);

                    if (ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(ImGuiMouseButton.Left)) {
                        currentDirectory = file;
                    }
                    ImGui.popID();
                    ImGui.popStyleColor();

                    ImGui.textWrapped(file.getName());
                    ImGui.nextColumn();
                } else if (!file.isHidden()) {
                    ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
                    ImGui.pushID(file.getName());

                    if (isImage(file)) {
                        var texture = TextureLibrary.uploadAndGet(file.toPath());
                        if (ImGui.imageButton(texture.getId(), iconSize, iconSize, 0, 1, 1, 0)) {
                        }
                    } else {
                        ImGui.imageButton(documentIconTexture.getId(), iconSize, iconSize, 0, 1, 1, 0);
                    }
                    if (ImGui.beginDragDropSource()) {
                        ImGui.setDragDropPayload(DragAndDropDataType.CONTENT_BROWSER_ITEM, file.getAbsolutePath());
                        ImGui.image(documentIconTexture.getId(), iconSize, iconSize, 0, 1, 1, 0);
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

    private boolean isImage(File file) {
        try {
            var contentType = Files.probeContentType(file.toPath());
            return contentType != null && contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}