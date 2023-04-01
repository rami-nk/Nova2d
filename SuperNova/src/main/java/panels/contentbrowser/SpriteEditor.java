package panels.contentbrowser;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;

public class SpriteEditor {

    private static final float[] gridLineColor = new float[]{0.5f, 0.5f, 0.5f, 1.0f};

    public static void create(ImageAsset imageAsset, Runnable onApply) {
        var openFlag = new ImBoolean(true);
        var flags = ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoScrollbar;
        ImGui.begin("Sprite Editor", openFlag, flags);
        ImGui.text(imageAsset.getName());

        var rows = new ImInt(imageAsset.getSpriteRows());
        if (ImGui.inputInt("Sprite Rows", rows)) {
            imageAsset.setSpriteRows(rows.get());
        }

        var columns = new ImInt(imageAsset.getSpriteColumns());
        if (ImGui.inputInt("Sprite Columns", columns)) {
            imageAsset.setSpriteColumns(columns.get());
        }

        var padding = new ImInt(imageAsset.getSpritePadding());
        if (ImGui.inputInt("Padding", padding)) {
            imageAsset.setSpritePadding(padding.get());
        }

        ImGui.colorEdit4("Grid color", gridLineColor);

        ImGui.dummy(0, 20);

        drawImageWithGrid(imageAsset, rows.get(), columns.get(), padding.get(), gridLineColor);

        ImGui.dummy(0, 20);

        if (ImGui.button("Apply")) {
            onApply.run();
            imageAsset.createSubTextures();
        }
        ImGui.end();
        if (!openFlag.get()) {
            onApply.run();
        }
    }

    private static void drawImageWithGrid(ImageAsset imageAsset, int rows, int columns, float padding, float[] gridColor) {
        var imageWidth = ImGui.getContentRegionAvailX();
        var imageHeight = (1.0f / imageAsset.getAspectRatio()) * imageWidth;

        float cellWidth = imageWidth / columns;
        float cellHeight = imageHeight / rows;

        var drawList = ImGui.getWindowDrawList();

        ImGui.image(imageAsset.getTextureId(), imageWidth, imageHeight, 0, 1, 1, 0);

        float imageX = ImGui.getItemRectMinX();
        float imageY = ImGui.getItemRectMinY();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                float x = imageX + j * cellWidth + padding;
                float y = imageY + i * cellHeight + padding;
                float x2 = x + cellWidth - 2 * padding;
                float y2 = y + cellHeight - 2 * padding;
                drawList.addRect(x, y, x2, y2, rgbaFloatArrayToColorInt(gridColor), 0);
            }
        }
    }

    public static int rgbaFloatArrayToColorInt(float[] rgba) {
        int r = (int) (rgba[0] * 255) & 0xFF;
        int g = (int) (rgba[1] * 255) & 0xFF;
        int b = (int) (rgba[2] * 255) & 0xFF;
        int a = (int) (rgba[3] * 255) & 0xFF;

        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}