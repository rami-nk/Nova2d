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

        final String nova2dLogoTextureId = "Nova2d-logo-white.png";
        final String openGlLogoTextureId = "openGlLogo.png";

        TextureProvider.uploadTexture(nova2dLogoTextureId);
        TextureProvider.uploadTexture(openGlLogoTextureId);

        var nova2dLogo = new GameObject(
                new Vector2f(-150, -150),
                new Vector2f(150, 150));
        nova2dLogo.addComponent(
                new Sprite(nova2dLogoTextureId)
        );
        addGameObjectToScene(nova2dLogo);

        var openGlLogo = new GameObject(
                new Vector2f(0, 0),
                new Vector2f(150, 150));
        openGlLogo.addComponent(
                new Sprite(openGlLogoTextureId)
        );
        addGameObjectToScene(openGlLogo);

        var nova2dLogo2 = new GameObject(
                new Vector2f(0, -150),
                new Vector2f(150, 150));
        nova2dLogo2.addComponent(
                new Sprite(nova2dLogoTextureId)
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