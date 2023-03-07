package io.nova.opengl.renderer;

import io.nova.core.renderer.FrameBuffer;
import io.nova.core.renderer.FrameBufferSpecification;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class OpenGLFrameBuffer implements FrameBuffer {

    public FrameBufferSpecification specification;
    private int rendererId;
    private int colorAttachment;
    private int depthAttachment;

    public OpenGLFrameBuffer(FrameBufferSpecification specification) {
        this.specification = specification;
        invalidate();
    }

    @Override
    public void invalidate() {
        rendererId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, rendererId);

        colorAttachment = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, colorAttachment);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, specification.width, specification.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorAttachment, 0);

        depthAttachment = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, depthAttachment);
        glTexImage2D(GL_TEXTURE_2D, 1, GL_DEPTH24_STENCIL8, specification.width, specification.height, 0, GL_DEPTH_STENCIL, GL_UNSIGNED_INT_24_8, (ByteBuffer) null);
        glFramebufferTexture2D(GL_TEXTURE_2D, GL_DEPTH_STENCIL_ATTACHMENT, GL_TEXTURE_2D, depthAttachment, 0);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("ERROR: Framebuffer is not complete!");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, rendererId);
        glViewport(0, 0, specification.width, specification.height);
    }

    @Override
    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public FrameBufferSpecification getSpecification() {
        return null;
    }

    @Override
    public int getColorAttachmentRendererId() {
        return colorAttachment;
    }
}