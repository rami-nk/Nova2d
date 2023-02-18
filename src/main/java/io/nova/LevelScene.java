package io.nova;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelScene extends Scene {

    private boolean isChanging;
    private double transitionTime = 0.2;

    @Override
    public void update(double deltaTime) {
        if (!isChanging && KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            isChanging = true;
            Nova2dWindow.getInstance().changeColorTo(0.0, 1.0, 1.0);
        }

        if (isChanging && transitionTime > 0) {
            transitionTime-= deltaTime;
        } else if (isChanging) {
            Nova2dWindow.getInstance().changeScene(0);
        }
    }
}
