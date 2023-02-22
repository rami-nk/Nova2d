package io.nova.core.scene;

import io.nova.core.Camera;
import io.nova.core.GameObject;
import io.nova.core.Nova2dWindow;
import io.nova.core.components.Sprite;
import io.nova.core.listener.KeyListener;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;

public class BatchScene extends Scene {

    private final Camera camera;

    BatchScene() {
        camera = new Camera(new Vector2f(250, 250));

        float sizeX = Nova2dWindow.getWidth() / 100.0f;
        float sizeY = Nova2dWindow.getHeight() / 100.0f;

        for (int x=0; x < 100; x++) {
            for (int y=0; y < 100; y++) {
                float xPos = (x * sizeX);
                float yPos = (y * sizeY);

                var gameObject = new GameObject(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY));
                gameObject.addComponent(new Sprite(new Vector4f(xPos / Nova2dWindow.getWidth(), yPos / Nova2dWindow.getHeight(), 1, 1)));
                this.addGameObjectToScene(gameObject);
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        var speed = 100;
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.move(new Vector2f((float)(-deltaTime * speed), 0));
        } else if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            camera.move(new Vector2f(0, (float)(-deltaTime * speed)));
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.move(new Vector2f(0, (float)(deltaTime * speed)));
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
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
