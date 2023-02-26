package io.nova.scenes;

import io.nova.renderer.Camera;
import io.nova.components.GameObject;
import io.nova.components.Sprite;
import io.nova.core.Scene;
import io.nova.utils.TextureProvider;
import org.joml.Vector2f;

public class ZoomTextureScene extends Scene {

    private final Camera camera;

    ZoomTextureScene() {
        camera = new Camera();

        var nova2dLogoTextureId = TextureProvider.uploadTexture("Nova2d-logo-white.png");
        var openGlLogoTextureId = TextureProvider.uploadTexture("openGlLogo.png");

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
        // TODO: event handling
//        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
//            camera.zoom((float) (1.0f + 1.0f * deltaTime));
//        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
//            camera.zoom((float) (1.0f - 1.0f * deltaTime));
//        }
    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}