package panels.contentbrowser;

import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import org.joml.Vector2f;

public class SubImageAsset {

    private final String name;
    private final SubTexture subTexture;
    private final Vector2f leftTop;
    private final Vector2f rightBottom;
    private final float width;
    private final float height;

    public SubImageAsset(String name, Texture texture, int x, int y, float width, float height) {
        this.name = name;
        this.subTexture = new SubTexture(texture, new Vector2f(x, y), new Vector2f(width, height));
        this.width = width;
        this.height = height;
        this.leftTop = new Vector2f(subTexture.getLeftTop()[0], subTexture.getLeftTop()[1]);
        this.rightBottom = new Vector2f(subTexture.getRightBottom()[0], subTexture.getRightBottom()[1]);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureId() {
        return subTexture.getTexture().getId();
    }

    public String getName() {
        return name;
    }

    public Vector2f getLeftTop() {
        return leftTop;
    }

    public Vector2f getRightBottom() {
        return rightBottom;
    }

    public SubTexture getSubTexture() {
        return subTexture;
    }
}