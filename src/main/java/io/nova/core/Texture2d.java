package io.nova.core;

import org.lwjgl.BufferUtils;

import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture2d {

    public static final String RESERVED_TEXTURE_SLOT_ID = "RESERVED";
    private final String filepath;
    private final int rendererId;

    public Texture2d(String filepath) {
        this.filepath = filepath;
        rendererId = glGenTextures();
        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        // pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        loadAndUploadTexture(filepath);
    }

    private static void loadAndUploadTexture(String filepath) {
        var width = BufferUtils.createIntBuffer(1);
        var height = BufferUtils.createIntBuffer(1);
        var channel = BufferUtils.createIntBuffer(1);
        var textureBytes = stbi_load(filepath, width, height, channel, 0);

        if (!Objects.isNull(textureBytes)) {
            int internalFormat = 0;
            int channelValue = channel.get();
            if (channelValue == 4) {
                internalFormat = GL_RGBA;
            } else if (channelValue == 3) {
                internalFormat = GL_RGB;
            } else {
                System.err.println("Format not supported!");
            }
            glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width.get(), height.get(),
                    0, internalFormat, GL_UNSIGNED_BYTE, textureBytes);
            stbi_image_free(textureBytes);
        } else {
            System.err.printf("Could not load texture for %s", filepath);
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, rendererId);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void activate(int slot) {
        glActiveTexture(slot);
    }
}