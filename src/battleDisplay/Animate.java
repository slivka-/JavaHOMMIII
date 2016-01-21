package battleDisplay;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by slivka on 21.01.2016.
 */
public class Animate
{
    private int frameCount;                 // Counts ticks for change
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // has animations stopped

    private List<AnimationFrame> _frames = new ArrayList<AnimationFrame>();    // Arraylist of frames

    public Animate(List<AnimationFrame> frames) {
        this.stopped = true;
        this._frames = frames;


        this.frameCount = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this._frames.size();

    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (_frames.size() == 0) {
            return;
        }

        stopped = false;
    }

    public void stop() {
        if (_frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (_frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return _frames.get(currentFrame).getFrame();
    }

    public void update() {
        if (!stopped) {
               currentFrame += animationDirection;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }

        }

    }
}
