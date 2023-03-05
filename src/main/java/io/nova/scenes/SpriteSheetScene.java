package io.nova.scenes;

import io.nova.components.GameObject;
import io.nova.components.Sprite;
import io.nova.components.SpriteSheet;
import io.nova.core.Scene;
import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.texture.TextureLibrary;
import io.nova.window.Input;
import org.joml.Vector2f;

import static io.nova.core.codes.KeyCodes.*;

public class SpriteSheetScene extends Scene {

    private final Camera camera;
    private final int numberOfCharacterPositions = 4;
    private int characterPosition = 0;
    private GameObject gameObject;
    private SpriteSheet spriteSheet;
    private int every5Loops = 5;

    SpriteSheetScene() {
        camera = new Camera();
        createAndAddAllSpritesToScene();
    }

    private void createAndAddAllSpritesToScene() {
        spriteSheet = new SpriteSheet(
                TextureLibrary.upload("spritesheet.png"),
                numberOfCharacterPositions
        );

        gameObject = new GameObject(
                new Vector2f(-64, -64),
                new Vector2f(128, 128)
        );
        gameObject.addComponent(spriteSheet.getSprites().get(characterPosition));
        addGameObjectToScene(gameObject);
    }

    @Override
    public void update(double deltaTime) {
        if (every5Loops == 5) {
            if (Input.isKeyPressed(NV_KEY_D)) {
                characterPosition = (characterPosition + 1) % numberOfCharacterPositions;
                var sprite = (Sprite) gameObject.getComponent(Sprite.class);
                var newSprite = spriteSheet.getSprites().get(characterPosition);
                sprite.setTextureCoordinates(newSprite.getTextureCoordinates());
                sprite.setPosition(sprite.getPosition().add(5, 0));
            }
        }

        var speed = 100;
        if (Input.isKeyPressed(NV_KEY_RIGHT)) {
            camera.move(new Vector2f((float) (-deltaTime * speed), 0));
        } else if (Input.isKeyPressed(NV_KEY_UP)) {
            camera.move(new Vector2f(0, (float) (-deltaTime * speed)));
        } else if (Input.isKeyPressed(NV_KEY_DOWN)) {
            camera.move(new Vector2f(0, (float) (deltaTime * speed)));
        } else if (Input.isKeyPressed(NV_KEY_LEFT)) {
            camera.move(new Vector2f((float) (deltaTime * speed), 0));
        }

        every5Loops = (every5Loops + 1) % 6;
    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}