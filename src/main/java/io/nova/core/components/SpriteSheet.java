package io.nova.core.components;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet extends Component {

    private List<Sprite> sprites;
    private int textureId;
    private int numberOfSprites;
    private int xSpacing;
    private int ySpacing;

    public SpriteSheet(int textureId, int numberOfSprites) {
        this(textureId, numberOfSprites, 0, 0);
    }

    public SpriteSheet(int textureId, int numberOfSprites, int xSpacing, int ySpacing) {
        this.sprites = new ArrayList<>();
        this.textureId = textureId;
        this.numberOfSprites = numberOfSprites;
        this.xSpacing = xSpacing;
        this.ySpacing = ySpacing;

        for (int n = 0; n < numberOfSprites; n++) {
            var w = 1.0f / numberOfSprites;
            var textureCoordinates = new Vector2f[]{
                    new Vector2f(w * (n + 1), 1),
                    new Vector2f(w * (n + 1), 0),
                    new Vector2f(w * n, 0),
                    new Vector2f(w * n, 1),
            };
            var sprite = new Sprite(textureId, textureCoordinates);
            sprites.add(sprite);
        }
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
}