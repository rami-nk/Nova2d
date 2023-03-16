package io.nova.core.renderer.texture;

import java.nio.ByteBuffer;

public interface Texture {

    int RESERVED_TEXTURE_SLOT_ID = -1;
    int SLOT_ZERO = 33984;

    void loadAndUploadTexture(String filepath);

    default void bind() {
        bind(0);
    }

    void bind(int slot);

    void unbind();

    void activate(int slot);

    void setData(ByteBuffer data);

    int getWidth();

    int getHeight();

    int getId();
}