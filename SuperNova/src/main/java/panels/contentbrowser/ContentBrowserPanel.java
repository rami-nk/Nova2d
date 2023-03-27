package panels.contentbrowser;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureLibrary;
import panels.DragAndDropDataType;

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
    private boolean showImageViewer = false;
    private ImageAsset selectedImageAsset;
    private boolean showSpriteEditor = false;

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

        if (showImageViewer) {
            createImageViewer();
        }

        ImGui.end();
        ImGui.popStyleVar();
    }

    private void createImageViewer() {
        ImGui.begin("Image viewer", new ImBoolean(true), ImGuiWindowFlags.NoScrollbar);
        ImGui.text(selectedImageAsset.getName());

        if (ImGui.beginCombo("Sprite Mode", selectedImageAsset.getSpriteMode().getDisplayName())) {
            for (var spriteMode : SpriteMode.values()) {
                var isSelected = spriteMode == selectedImageAsset.getSpriteMode();
                if (ImGui.selectable(spriteMode.getDisplayName(), isSelected)) {
                    selectedImageAsset.setSpriteMode(spriteMode);
                }
                if (isSelected) {
                    ImGui.setItemDefaultFocus();
                }
            }
            ImGui.endCombo();
        }
        if (ImGui.button("Sprite Editor")) {
            showSpriteEditor = true;
        }
        if (showSpriteEditor) {
            createSpriteEditor();
        }
        if (selectedImageAsset.isSpriteSheet()) {
            for (var subTexture : selectedImageAsset.getSubImages()) {
                ImGui.imageButton(subTexture.getTextureId(), subTexture.getWidth(), subTexture.getHeight(), subTexture.getLeftTop().x, subTexture.getLeftTop().y, subTexture.getRightBottom().x, subTexture.getRightBottom().y);
                ImGui.sameLine();
                ImGui.text(subTexture.getName());
            }
        }

        var imageWidth = ImGui.getContentRegionAvailX();
        var imageHeight = ImGui.getContentRegionAvailY();
        var aspectRatio = selectedImageAsset.getAspectRatio();
        if (imageWidth > imageHeight) {
            imageWidth = imageHeight * aspectRatio;
        } else {
            imageHeight = imageWidth / aspectRatio;
        }
        ImGui.spacing();
        ImGui.spacing();
        ImGui.image(selectedImageAsset.getTextureId(), imageWidth, imageHeight, 0, 1, 1, 0);

        ImGui.end();
    }

    private void createSpriteEditor() {
        var flags = ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoScrollbar;
        ImGui.begin("Sprite Editor", new ImBoolean(true), flags);
        ImGui.text(selectedImageAsset.getName());

        var rows = new ImInt(selectedImageAsset.getSpriteRows());
        if (ImGui.inputInt("Sprite Rows", rows)) {
            selectedImageAsset.setSpriteRows(rows.get());
        }

        var columns = new ImInt(selectedImageAsset.getSpriteColumns());
        if (ImGui.inputInt("Sprite Columns", columns)) {
            selectedImageAsset.setSpriteColumns(columns.get());
        }

        var imageWidth = ImGui.getContentRegionAvailX();
        var imageHeight = ImGui.getContentRegionAvailY();
        var aspectRatio = selectedImageAsset.getAspectRatio();
        if (imageWidth > imageHeight) {
            imageWidth = imageHeight * aspectRatio;
        } else {
            imageHeight = imageWidth / aspectRatio;
        }
        ImGui.spacing();
        ImGui.spacing();
        ImGui.image(selectedImageAsset.getTextureId(), imageWidth, imageHeight, 0, 1, 1, 0);

        if (ImGui.button("Apply")) {
            showSpriteEditor = false;
            selectedImageAsset.createSubTextures();
        }
        ImGui.end();
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
                        var imageAsset = AssetManager.getImageAsset(file);
                        if (ImGui.imageButton(imageAsset.getTextureId(), iconSize, iconSize, 0, 1, 1, 0)) {
                            showImageViewer = true;
                            selectedImageAsset = imageAsset;
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