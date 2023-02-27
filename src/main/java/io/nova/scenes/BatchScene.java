package io.nova.scenes;

import io.nova.components.Sprite;
import io.nova.renderer.Camera;
import io.nova.components.GameObject;
import io.nova.core.application.Application;
import io.nova.core.Scene;
import io.nova.window.Input;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static io.nova.core.KeyCodes.*;

public class BatchScene extends Scene {

    private final Camera camera;

    BatchScene() {
        camera = new Camera();

        float objectWidth = Application.getWindow().getWidth() / 100.0f;
        float objectHeight = Application.getWindow().getHeight() / 100.0f;
        float offset = -250.0f;

        for (int x=0; x < 100; x++) {
            for (int y=0; y < 100; y++) {
                float xPos = offset + (x * objectWidth);
                float yPos = offset + (y * objectHeight);

                var gameObject = new GameObject(new Vector2f(xPos, yPos), new Vector2f(objectWidth, objectHeight));
                gameObject.addComponent(new Sprite(new Vector4f((objectWidth * x) / Application.getWindow().getWidth(), (objectHeight * y) / Application.getWindow().getHeight(), 1, 1)));
                this.addGameObjectToScene(gameObject);
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        var speed = 100;
        if (Input.isKeyPressed(NV_KEY_RIGHT)) {
            camera.move(new Vector2f((float)(-deltaTime * speed), 0));
        } else if (Input.isKeyPressed(NV_KEY_UP)) {
            camera.move(new Vector2f(0, (float)(-deltaTime * speed)));
        } else if (Input.isKeyPressed(NV_KEY_DOWN)) {
            camera.move(new Vector2f(0, (float)(deltaTime * speed)));
        } else if (Input.isKeyPressed(NV_KEY_LEFT)) {
            camera.move(new Vector2f((float)(deltaTime * speed), 0));
        }

        for (GameObject gameObject : getGameObjects()) {
            gameObject.update(deltaTime);
        }
    }

    @Override
    public void render() {
        getBatchRenderer().render(camera);
    }
}