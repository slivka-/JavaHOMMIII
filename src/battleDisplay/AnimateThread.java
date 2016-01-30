package battleDisplay;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slivka on 21.01.2016.
 */
public class AnimateThread implements Runnable
{
    private int currentFrame;               // animations current fram
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // has animations stopped

    private List<BufferedImage> _frames = new ArrayList<BufferedImage>();    // Arraylist of frames

    public AnimateThread(List<BufferedImage> frames) {
        this.stopped = false;
        this._frames = frames;


        this.currentFrame = 0;
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
        this.currentFrame = 0;
    }

    public BufferedImage getFrame()
    {
        return _frames.get(currentFrame);
    }

     @Override
    public void run()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                if (!stopped)
                {
                    currentFrame += 1;

                    if (currentFrame > totalFrames - 1)
                    {
                        currentFrame = 0;
                    } else if (currentFrame < 0)
                    {
                        currentFrame = totalFrames - 1;
                    }
                    Thread.sleep(90);
                    //System.out.println(currentFrame);
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }



}
