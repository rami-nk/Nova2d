package io.nova.opengl.renderer;

import io.nova.core.renderer.texture.Texture;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class OpenGLTexture implements Texture {

    private final int rendererId;
    private String filepath;
    private int width;
    private int height;
    private int internalFormat;

    public OpenGLTexture(Path path) {
        this.filepath = path.toString();
        rendererId = glGenTextures();
        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        loadAndUploadTexture(filepath);
    }

    public OpenGLTexture(int width, int height) {
        this.width = width;
        this.height = height;
        this.internalFormat = GL_RGBA;
        this.rendererId = glGenTextures();

        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
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

            int channelValue = channel.get();
            if (channelValue == 4) {
                internalFormat = GL_RGBA;
            } else if (channelValue == 3) {
                internalFormat = GL_RGB;
            } else {
                System.err.println("Format not supported!");
            }
            setData(textureBytes);
            stbi_image_free(textureBytes);
        } else {
            System.err.printf("Could not load texture for %s", filepath);
        }
    }

    @Override
    public void bind(int slot) {
        glActiveTexture(slot);
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
    public void setData(ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height,
                0, internalFormat, GL_UNSIGNED_BYTE, data);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getId() {
        return rendererId;
    }
}