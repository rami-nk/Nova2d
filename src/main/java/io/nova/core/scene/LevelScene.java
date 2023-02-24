package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.GameObject;
import io.nova.core.components.Sprite;
import io.nova.core.utils.TextureProvider;
import org.joml.Vector2f;

public class LevelScene extends Scene {

    private final Camera camera;

    LevelScene() {
        camera = new Camera();

        var nova2dLogo = new GameObject(
                new Vector2f(-150, -150),
                new Vector2f(150, 150));
        nova2dLogo.addComponent(
                new Sprite(TextureProvider.uploadTexture("Nova2d-logo-white.png"))
        );
        addGameObjectToScene(nova2dLogo);

        var openGlLogo = new GameObject(
                new Vector2f(0, 0),
                new Vector2f(150, 150));
        openGlLogo.addComponent(
                new Sprite(TextureProvider.uploadTexture("openGlLogo.png"))
        );
        addGameObjectToScene(openGlLogo);

        var nova2dLogo2 = new GameObject(
                new Vector2f(0, -150),
                new Vector2f(150, 150));
        nova2dLogo2.addComponent(
                new Sprite(TextureProvider.uploadTexture("Nova2d-logo-white.png"))
        );
        addGameObjectToScene(nova2dLogo2);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}