package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.GameObject;
import io.nova.core.components.Sprite;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelScene extends Scene {

    private final Camera camera;

    LevelScene() {
        camera = new Camera();

        var nova2dLogo = new GameObject(
                new Vector2f(-75, -75),
                new Vector2f(150, 150));
        nova2dLogo.addComponent(
                new Sprite(new Vector4f(0, 0, 0, 1))
        );
        addGameObjectToScene(nova2dLogo);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}