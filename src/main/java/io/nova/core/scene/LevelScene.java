package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.GameObject;
import io.nova.core.components.Sprite;
import io.nova.core.listener.KeyListener;
import io.nova.core.utils.TextureProvider;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class LevelScene extends Scene {

    private final Camera camera;

    LevelScene() {
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
        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            camera.zoom((float) (1.0f + 1.0f * deltaTime));
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.zoom((float) (1.0f - 1.0f * deltaTime));
        }
    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}