package HeroDisplay;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by slivka on 06.02.2016.
 */
public class HeroAnimationThread implements Runnable,Serializable
{

    private int currentFrame;
    private boolean stopped;

    private BufferedImage standingFront;
    private BufferedImage standingBack;
    private BufferedImage standingSideways;

    private ArrayList<BufferedImage> moveFront;
    private ArrayList<BufferedImage> moveBack;
    private ArrayList<BufferedImage> moveSideways;

    public HeroAnimationThread(int heroRow)
    {
        moveFront = new ArrayList<BufferedImage>();
        moveBack = new ArrayList<BufferedImage>();
        moveSideways = new ArrayList<BufferedImage>();
        stopped = true;
        currentFrame = 0;
        try
        {
            int x=0;
            File spritesheet = new File("assets\\heroes\\HEROSAVE.png");
            BufferedImage _sSheet = ImageIO.read(spritesheet);
            standingFront = _sSheet.getSubimage(96*x, 64*heroRow, 96, 64);
            x++;
            standingSideways = _sSheet.getSubimage(96*x, 64*heroRow, 96, 64);
            x++;
            standingBack = _sSheet.getSubimage(96*x, 64*heroRow, 96, 64);
            x++;
            while(x<11)
            {
                moveFront.add(_sSheet.getSubimage(96*x, 64*heroRow, 96, 64));
                x++;
            }
            while(x<19)
            {
                moveSideways.add(_sSheet.getSubimage(96*x, 64*heroRow, 96, 64));
                x++;
            }
            while(x<27)
            {
                moveBack.add(_sSheet.getSubimage(96*x, 64*heroRow, 96, 64));
                x++;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void restart() {
        stopped = false;
        currentFrame = 0;
    }

    private void stop()
    {
        stopped = true;
    }

    public BufferedImage getCurrentFrame(HeroDirection direction)
    {
        BufferedImage output;
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        AffineTransformOp op;
        try
        {
            switch (direction)
            {
                case IDLE_BACK:
                    output = standingBack;
                    this.stop();
                    break;

                case IDLE_FRONT:
                    output = standingFront;
                    this.stop();
                    break;

                case IDLE_RIGHT:
                    output = standingSideways;
                    this.stop();
                    break;

                case IDLE_LEFT:
                    output = standingSideways;
                    tx.translate(-output.getWidth(null), 0);
                    op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    output = op.filter(output, null);
                    this.stop();
                    break;

                case MOVING_BACK:
                    output = moveBack.get(currentFrame);
                    if (stopped)
                    {
                        this.restart();
                    }
                    break;

                case MOVING_FRONT:
                    output = moveFront.get(currentFrame);
                    if (stopped)
                    {
                        this.restart();
                    }
                    break;

                case MOVING_RIGHT:
                    output = moveSideways.get(currentFrame);
                    if (stopped)
                    {
                        this.restart();
                    }
                    break;

                case MOVING_LEFT:
                    output = moveSideways.get(currentFrame);
                    tx.translate(-output.getWidth(null), 0);
                    op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    output = op.filter(output, null);
                    if (stopped)
                    {
                        this.restart();
                    }
                    break;

                default:
                    output = null;
                    break;
            }
            return output;
        }
        catch (NullPointerException ex)
        {
            return standingSideways;
        }

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

                    if (currentFrame > 7)
                    {
                        currentFrame = 0;
                    }
                    else if (currentFrame < 0)
                    {
                        currentFrame = 8;
                    }
                    Thread.sleep(90);
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

}
