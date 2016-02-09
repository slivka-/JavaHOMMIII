package HeroDisplay;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by slivka on 09.02.2016.
 */
public class HeroMoveThread implements Runnable
{
    private ArrayList<Point> _path;
    private int pathPointCount = 0;
    private int animationCount = 0;
    private int endOfPath;
    private Point2D currentPosition;
    private boolean isRunning;
    private HeroDirection direction;


    public HeroMoveThread(ArrayList<Point> path)
    {
        this._path = path;
        this.endOfPath = _path.size();
        this.currentPosition = new Point();
        this.isRunning = true;
    }


    public Point2D getCurrentPosition()
    {
        return currentPosition;
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    private double calculate1DProgress(int from, int to, int frame)
    {
        int distance = to - from;
        float piece = distance/10;
        float next = from + (piece * frame);
        return next;
    }

    public HeroDirection getDirection()
    {
        return direction;
    }

    @Override
    public void run()
    {
        while(pathPointCount+1 != endOfPath)
        {
            try
            {
                double nextX = calculate1DProgress(_path.get(pathPointCount).x, _path.get(pathPointCount + 1).x, animationCount);
                double nextY = calculate1DProgress(_path.get(pathPointCount).y, _path.get(pathPointCount + 1).y, animationCount);
                if(nextX>currentPosition.getX())
                {
                    direction = HeroDirection.MOVING_RIGHT;
                }
                else if(nextX<currentPosition.getX())
                {
                    direction = HeroDirection.MOVING_LEFT;
                }
                else if(nextX==currentPosition.getX())
                {
                    if(nextY>currentPosition.getY())
                    {
                        direction = HeroDirection.MOVING_FRONT;
                    }
                    else if(nextY<currentPosition.getY())
                    {
                        direction = HeroDirection.MOVING_BACK;
                    }
                }
                currentPosition.setLocation(nextX, nextY);
                if (animationCount < 9)
                {
                    animationCount++;
                } else
                {
                    animationCount = 0;
                    pathPointCount++;
                }
                Thread.sleep(25);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        isRunning = false;
    }
}
