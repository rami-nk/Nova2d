package panels.contentbrowser;

import io.nova.core.renderer.texture.Texture;
import io.nova.core.renderer.texture.TextureLibrary;

import java.io.File;

public class ImageAsset {

    private final String name;
    private final File file;
    private final Texture texture;
    private final int width;
    private final int height;
    private SpriteMode spriteMode;
    private SubImageAsset[] subImages;
    private int spriteRows, spriteColumns;
    private int spritePadding;

    public ImageAsset(File file) {
        this.file = file;
        this.name = file.getName();
        this.spriteMode = SpriteMode.SINGLE;
        this.texture = TextureLibrary.uploadTexture(file.toPath());

        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public SubImageAsset[] getSubImages() {
        return subImages;
    }

    public boolean isSpriteSheet() {
        return spriteMode == SpriteMode.MULTIPLE && subImages != null;
    }

    public boolean isSpriteSheetModeMultiple() {
        return spriteMode == SpriteMode.MULTIPLE;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getAspectRatio() {
        return (float) width / (float) height;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public SpriteMode getSpriteMode() {
        return spriteMode;
    }

    public void setSpriteMode(SpriteMode spriteMode) {
        this.spriteMode = spriteMode;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getTextureId() {
        return texture.getId();
    }

    public int getSpriteRows() {
        return spriteRows;
    }

    public void setSpriteRows(int rows) {
        this.spriteRows = rows;
    }

    public int getSpriteColumns() {
        return spriteColumns;
    }

    public void setSpriteColumns(int columns) {
        this.spriteColumns = columns;
    }

    public void createSubTextures() {
        if (spriteMode == SpriteMode.SINGLE) {
            return;
        }

        subImages = new SubImageAsset[spriteRows * spriteColumns];

        var height = (float) texture.getHeight() / spriteRows;
        var width = (float) texture.getWidth() / spriteColumns;

        int index = 0;
        for (int y = 0; y < spriteRows; y++) {
            for (int x = 0; x < spriteColumns; x++) {
                var name = file.getName().split("\\.")[0] + "-" + index;
                subImages[index++] = new SubImageAsset(name, texture, x + spritePadding, y + spritePadding, width, height);
            }
        }
    }

    public int getSpriteCount() {
        return subImages.length;
    }


    public int getSpritePadding() {
        return spritePadding;
    }

    public void setSpritePadding(int padding) {
        this.spritePadding = padding;
    }
}