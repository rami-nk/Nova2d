package io.nova.core.renderer.texture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.nio.ByteBuffer;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface Texture {

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

    @JsonIgnore
    int getId();

    String getFilepath();
}