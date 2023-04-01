package io.nova.ecs.component;

import java.util.ArrayList;
import java.util.List;

public class AnimationComponent extends Component {
    private final List<AnimationClip> clips;
    private AnimationClip currentClip;
    private boolean playing;

    public AnimationComponent() {
        this.clips = new ArrayList<>();
    }

    public void addClip(AnimationClip clip) {
        clips.add(clip);
    }

    public List<AnimationClip> getClips() {
        return clips;
    }

    public AnimationClip getCurrentClip() {
        return currentClip;
    }

    public void setCurrentClip(AnimationClip currentClip) {
        this.currentClip = currentClip;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void stop() {
        playing = false;
    }

    public void play() {
        playing = true;
        currentClip.playNextFrame();
    }
}