package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.GameObject;
import io.nova.core.components.Sprite;
import io.nova.core.utils.TextureProvider;
import org.joml.Vector2f;

public class Nova2dLogoScene extends Scene {

    private final Camera camera;

    Nova2dLogoScene() {
        camera = new Camera(500, 500);
        var textureId = "Nova2d-logo-white.png";
        TextureProvider.uploadTexture(textureId);

        var gameObject = new GameObject(
                new Vector2f(-206, -160),
                new Vector2f(411, 321)
        );
        gameObject.addComponent(new Sprite(textureId));
        addGameObjectToScene(gameObject);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}