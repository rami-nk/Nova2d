package io.nova.core.renderer;

import io.nova.opengl.renderer.OpenGLTexture;

import java.nio.ByteBuffer;

public interface Texture {
    int RESERVED_TEXTURE_SLOT_ID = -1;

    static Texture create(String path) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLTexture(path);
            }
            case None -> {
                System.err.printf("%s not supported!\n", Renderer.API.name());
                return null;
            }
        }
        return null;
    }

    static Texture create(int width, int height) {
        switch (Renderer.API) {
            case OpenGL -> {
                return new OpenGLTexture(width, height);
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
    void setData(ByteBuffer data);
    int getWidth();
    int getHeight();
}