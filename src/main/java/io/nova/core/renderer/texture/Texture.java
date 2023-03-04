package io.nova.core.renderer.texture;

import java.nio.ByteBuffer;

public interface Texture {

    int RESERVED_TEXTURE_SLOT_ID = -1;

    void loadAndUploadTexture(String filepath);

    void bind();

    void unbind();

    void activate(int slot);

    void setData(ByteBuffer data);

    int getWidth();

    int getHeight();
}