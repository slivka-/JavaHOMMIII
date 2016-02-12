package battleDisplay;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by slivka on 30.01.2016.
 */
public class MoveThread implements Runnable
{

    private ArrayList<Point> _path;
    private int pathPointCount;
    private int animationCount;
    private int endOfPath;
    private Point2D currentPosition;
    private boolean isRunning;
    private boolean isFacingLeft;


    public MoveThread(ArrayList<Point> path)
    {
        this._path = path;
        this.endOfPath = _path.size();
        this.currentPosition = new Point();
        this.isRunning = true;
        this.pathPointCount = 0;
        this.animationCount = 0;
    }


    public Point2D getCurrentPosition()
    {
        return currentPosition;
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public boolean isFacingLeft()
    {
        return isFacingLeft;
    }

    private double calculate1DProgress(int from, int to, int frame)
    {
        int distance = to - from;
        float piece = distance/10;
        float next = from + (piece * frame);
        return next;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                double nextX = calculate1DProgress(_path.get(pathPointCount).x, _path.get(pathPointCount + 1).x, animationCount);
                if(nextX - currentPosition.getX()>0)
                {
                    isFacingLeft = false;
                }
                else
                {
                    isFacingLeft = true;
                }
                double nextY = calculate1DProgress(_path.get(pathPointCount).y, _path.get(pathPointCount + 1).y, animationCount);
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
            if(pathPointCount+1 >= endOfPath)
            {
                isRunning = false;
                break;
            }
        }

    }


}
