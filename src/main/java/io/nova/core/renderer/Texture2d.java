package io.nova.core.renderer;

public interface Texture2d {
    int RESERVED_TEXTURE_SLOT_ID = -1;

    void loadAndUploadTexture(String filepath);

    void bind();

    void unbind();

    void activate(int slot);

    int getWidth();

    int getHeight();
}