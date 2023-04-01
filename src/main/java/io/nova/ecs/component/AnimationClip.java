package io.nova.ecs.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.nova.core.renderer.texture.SubTexture;

import java.util.ArrayList;
import java.util.List;

public class AnimationClip {
    @JsonProperty
    private List<SubTexture> frames;
    @JsonProperty
    private int samples = 12;
    @JsonProperty
    private String name;
    @JsonProperty
    private int currentFrame = 0;

    private int currentTextureIndex = 0;

    private SubTexture currentFrameTexture;

    public AnimationClip() {
        this.frames = new ArrayList<>();
    }

    public SubTexture getCurrentFrameTexture() {
        return currentFrameTexture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFrame(SubTexture frame) {
        frames.add(frame);
    }

    public List<SubTexture> getFrames() {
        return frames;
    }

    public void setFrames(List<SubTexture> animationFrames) {
        this.frames = animationFrames;
    }

    public void playNextFrame() {
        if (frames.size() == 0) {
            return;
        }
        currentFrame++;
        if (currentFrame % 60 == 0) {
            currentFrame = currentTextureIndex = 0;
        }
        if (currentFrame % samples == 0) {
            currentTextureIndex++;
        }
        currentFrameTexture = frames.get(currentTextureIndex % frames.size());
    }

    public void setSamples(int samples) {
        this.samples = samples;
    }
}