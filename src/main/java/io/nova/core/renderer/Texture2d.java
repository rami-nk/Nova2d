package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLTexture2d;

public interface Texture2d {
    int RESERVED_TEXTURE_SLOT_ID = -1;

    static Texture2d create(String path) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLTexture2d(path);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }

    void loadAndUploadTexture(String filepath);

    void bind();

    void unbind();

    void activate(int slot);

    int getWidth();

    int getHeight();
}