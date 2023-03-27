package io.nova.ecs.component;

import io.nova.core.renderer.texture.SubTexture;
import io.nova.core.renderer.texture.Texture;
import org.joml.Vector4f;

public class SpriteRendererComponent extends Component {

    private float[] color;
    private Texture texture;
    private SubTexture subTexture;
    private float tilingFactor;

    public SpriteRendererComponent() {
        this.color = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
        this.tilingFactor = 1.0f;
    }

    public SubTexture getSubTexture() {
        return subTexture;
    }

    public void setSubTexture(SubTexture subTexture) {
        this.texture = null;
        this.subTexture = subTexture;
    }

    public float getTilingFactor() {
        return tilingFactor;
    }

    public void setTilingFactor(float tilingFactor) {
        this.tilingFactor = tilingFactor;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public float[] getColor() {
        return color;
    }

    public void setColor(float[] colorArray) {
        this.color = colorArray;
    }

    public Vector4f getColorAsVec() {
        return new Vector4f(color);
    }
}