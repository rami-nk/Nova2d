package io.nova.opengl.renderer;

import io.nova.core.renderer.framebuffer.FrameBuffer;
import io.nova.core.renderer.framebuffer.FrameBufferSpecification;
import io.nova.core.renderer.framebuffer.FrameBufferTextureFormat;
import io.nova.core.renderer.framebuffer.FrameBufferTextureSpecification;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class OpenGLFrameBuffer implements FrameBuffer {

    private final List<FrameBufferTextureSpecification> colorAttachmentSpecs;
    public FrameBufferSpecification specification;
    private int rendererId;
    private int depthAttachment;
    private FrameBufferTextureSpecification depthAttachmentSpec;
    private List<Integer> colorAttachments;

    public OpenGLFrameBuffer(FrameBufferSpecification specification) {
        this.specification = specification;
        this.colorAttachmentSpecs = new ArrayList<>();
        this.colorAttachments = new ArrayList<>();

        for (var spec : specification.getAttachments().getAttachments()) {
            if (!isDepthFormat(spec.getFormat())) {
                colorAttachmentSpecs.add(spec);
            } else {
                depthAttachmentSpec = spec;
            }
        }

        invalidate();
    }

    public boolean isDepthFormat(FrameBufferTextureFormat format) {
        return Objects.requireNonNull(format) == FrameBufferTextureFormat.DEPTH24STENCIL8;
    }

    @Override
    public void invalidate() {
        if (rendererId != 0) {
            dispose();
        }

        rendererId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, rendererId);

        if (colorAttachmentSpecs.size() != 0) {
            createTextures();

            for (int i = 0; i < colorAttachmentSpecs.size(); i++) {
                bindTexture(colorAttachments.get(i));

                switch (colorAttachmentSpecs.get(i).getFormat()) {
                    case RGBA8 -> attachColorTexture(colorAttachments.get(i), GL_RGBA8, GL_RGBA, i);
                    case RED_INTEGER -> attachColorTexture(colorAttachments.get(i), GL_R32I, GL_RED_INTEGER, i);
                }
            }
        }

        if (depthAttachmentSpec.getFormat() != FrameBufferTextureFormat.NONE) {
            depthAttachment = glGenTextures();
            bindTexture(depthAttachment);

            glTexImage2D(GL_TEXTURE_2D, 1, GL_DEPTH24_STENCIL8, specification.getWidth(), specification.getHeight(), 0, GL_DEPTH_STENCIL, GL_UNSIGNED_INT_24_8, (ByteBuffer) null);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            glFramebufferTexture2D(GL_TEXTURE_2D, GL_DEPTH_STENCIL_ATTACHMENT, GL_TEXTURE_2D, depthAttachment, 0);
        }

        if (colorAttachments.size() > 1) {
            int[] attachments = new int[colorAttachments.size()];
            for (int i = 0; i < colorAttachments.size(); i++) {
                attachments[i] = GL_COLOR_ATTACHMENT0 + i;
            }
            glDrawBuffers(attachments);
        } else if (colorAttachments.size() == 0) {
            glDrawBuffer(GL_NONE);
        }

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            System.err.println("ERROR: Framebuffer is not complete!");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, rendererId);
        glViewport(0, 0, specification.getWidth(), specification.getHeight());
    }

    @Override
    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void dispose() {
        glDeleteFramebuffers(rendererId);
        for (int colorAttachment : colorAttachments) {
            glDeleteTextures(colorAttachment);
        }
        glDeleteTextures(depthAttachment);
        colorAttachments = new ArrayList<>();
        depthAttachment = 0;
    }

    @Override
    public FrameBufferSpecification getSpecification() {
        return specification;
    }

    @Override
    public int getColorAttachmentRendererId(int index) {
        return colorAttachments.get(index);
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0 || width > 8192 || height > 8192) {
            System.err.println("ERROR: Attempted to resize framebuffer to " + width + ", " + height);
            return;
        }
        specification.setWidth(width);
        specification.setHeight(height);

        invalidate();
    }

    public int readPixel(int attachmentIndex, int x, int y) {
        glReadBuffer(GL_COLOR_ATTACHMENT0 + attachmentIndex);
        var buffer = BufferUtils.createIntBuffer(1);
        glReadPixels(x, y, 1, 1, GL_RED_INTEGER, GL_INT, buffer);
        return buffer.get();
    }

    private void attachColorTexture(int attachmentId, int internalFormat, int format, int i) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, specification.getWidth(), specification.getHeight(), 0, format, GL_UNSIGNED_BYTE, (ByteBuffer) null);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, attachmentId, 0);
    }

    private void createTextures() {
        for (var ignored : colorAttachmentSpecs) {
            colorAttachments.add(glGenTextures());
        }
    }

    private void bindTexture(int attachmentId) {
        glBindTexture(GL_TEXTURE_2D, attachmentId);
    }
}