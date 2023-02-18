package io.nova.core.scene;

import io.nova.core.Nova2dWindow;

public class LevelScene extends Scene {

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        Nova2dWindow.getInstance().changeColorTo(0.0, 1.0, 1.0);
    }
}
