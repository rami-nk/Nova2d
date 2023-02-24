package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.GameObject;
import io.nova.core.components.SpriteSheet;
import io.nova.core.utils.TextureProvider;
import org.joml.Vector2f;

public class SpriteSheetScene extends Scene {

    private final Camera camera;

    SpriteSheetScene() {
        camera = new Camera(new Vector2f(-200, 0));

        createAndAddAllSpritesToScene();
    }

    private void createAndAddAllSpritesToScene() {
        int numberOfSprites = 4;
        var spriteSheet = new SpriteSheet(
                TextureProvider.uploadTexture("spritesheet.png"),
                numberOfSprites
        );

        for (int i = 0; i < numberOfSprites; i++) {
            var gameObject = new GameObject(
                    new Vector2f(-64 * (2 * i + 1), -64),
                    new Vector2f(128, 128)
            );
            gameObject.addComponent(spriteSheet.getSprites().get(i));
            addGameObjectToScene(gameObject);
        }
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}