package io.nova.renderer;

import io.nova.core.renderer.Texture2d;
import org.lwjgl.BufferUtils;

import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class OpenGLTexture2d implements Texture2d {

    private final String filepath;
    private final int rendererId;
    private int width;
    private int height;

    public OpenGLTexture2d(String filepath) {
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

    @Override
    public void loadAndUploadTexture(String filepath) {
        var width = BufferUtils.createIntBuffer(1);
        var height = BufferUtils.createIntBuffer(1);
        var channel = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);

        var textureBytes = stbi_load(filepath, width, height, channel, 0);

        if (!Objects.isNull(textureBytes)) {
            this.width = width.get(0);
            this.height = height.get(0);

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

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, rendererId);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void activate(int slot) {
        glActiveTexture(slot);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}