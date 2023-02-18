package io.nova.scene;

import io.nova.Nova2dWindow;

public class LevelEditorScene extends Scene {

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        Nova2dWindow.getInstance().changeColorTo(1.0, 0.0, 0.0);
    }
}