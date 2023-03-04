package io.nova.scenes;

import io.nova.components.GameObject;
import io.nova.components.Sprite;
import io.nova.core.Scene;
import io.nova.core.renderer.camera.Camera;
import io.nova.core.renderer.texture.TextureLibrary;
import org.joml.Vector2f;

public class Nova2dLogoScene extends Scene {

    private final Camera camera;

    Nova2dLogoScene() {
        camera = new Camera(500, 500);
        var textureId = TextureLibrary.upload("Nova2d-logo-white.png");

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