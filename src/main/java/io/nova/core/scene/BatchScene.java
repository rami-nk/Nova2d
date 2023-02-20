package io.nova.core.scene;


import io.nova.core.GameObject;
import io.nova.core.components.Sprite;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class BatchScene extends Scene {

    BatchScene() {

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float)(600 - xOffset * 2);
        float totalHeight = (float)(300 - yOffset * 2);
        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeight / 100.0f;
        float padding = 3;

        for (int x=0; x < 100; x++) {
            for (int y=0; y < 100; y++) {
                float xPos = xOffset + (x * sizeX) + (padding * x);
                float yPos = yOffset + (y * sizeY) + (padding * y);

                var gameObject = new GameObject(new Vector2f(xPos, yPos));
                gameObject.addComponent(new Sprite(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
                this.addGameObjectToScene(gameObject);
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        System.out.println("FPS: " + (1.0f / deltaTime));

        for (GameObject gameObject : getGameObjects()) {
            gameObject.update(deltaTime);
        }
    }

    @Override
    public void render() {
        getBatchRenderer().render();
    }
}
