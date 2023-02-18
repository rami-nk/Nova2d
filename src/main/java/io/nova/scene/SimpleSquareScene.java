package io.nova.scene;

import io.nova.shader.Shader;

public class SimpleSquareScene extends Scene {

    private final Shader shader;

    SimpleSquareScene() {
        shader = new Shader("shaders/default.glsl");
        shader.bind();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {

    }
}
