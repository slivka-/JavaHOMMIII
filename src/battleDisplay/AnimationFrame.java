package battleDisplay;

import java.awt.image.BufferedImage;

/**
 * Created by slivka on 21.01.2016.
 */
public class AnimationFrame
{
    private BufferedImage _frame;
    private int _duration;

    public AnimationFrame(BufferedImage frame, int duration)
    {
        this._frame = frame;
        this._duration = duration;
    }

    public void setFrame(BufferedImage frame)
    {
        this._frame = frame;
    }

    public BufferedImage getFrame()
    {
        return _frame;
    }

    public int getDuration()
    {
        return _duration;
    }

    public void setDuration(int duration)
    {
        this._duration = duration;
    }


}
