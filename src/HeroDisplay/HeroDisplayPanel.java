package HeroDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by slivka on 06.02.2016.
 */
public class HeroDisplayPanel extends JPanel
{

    private ExecutorService ex;
    private HeroAnimationThread animation;
    private HeroMoveThread moveThread;
    private HeroDirection currentDirection;
    private boolean isMoving;

    public HeroDisplayPanel(int HeroID)
    {
        //this.setLayout(null);
        this.currentDirection = HeroDirection.IDLE_LEFT;
        ex = Executors.newFixedThreadPool(2);
        animation = new HeroAnimationThread(HeroID);
        ex.execute(animation);
        this.setOpaque(false);
        this.refreshDisplay();
    }

    private void refreshDisplay()
    {
        Timer timer = new Timer(40, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                revalidate();
                repaint();

            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }

    private void setCurrentDirection(HeroDirection direction)
    {
        this.currentDirection = direction;
    }

    private HeroDirection getCurrentDirection()
    {
        return this.currentDirection;
    }

    public void MoveHero(ArrayList<Point> path)
    {
        moveThread = new HeroMoveThread(path);
        ex.execute(moveThread);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if(moveThread != null)
        {
            if(moveThread.isRunning())
            {
                Point2D p = moveThread.getCurrentPosition();
                currentDirection = moveThread.getDirection();
                this.setBounds((int)p.getX()-32,(int)p.getY()-16,96,64);
            }
            else
            {
                if(currentDirection == HeroDirection.MOVING_BACK)
                {
                    currentDirection = HeroDirection.IDLE_BACK;
                }
                else if(currentDirection == HeroDirection.MOVING_FRONT)
                {
                    currentDirection = HeroDirection.IDLE_FRONT;
                }
                else if(currentDirection == HeroDirection.MOVING_LEFT)
                {
                    currentDirection = HeroDirection.IDLE_LEFT;
                }
                else if(currentDirection == HeroDirection.MOVING_RIGHT)
                {
                    currentDirection = HeroDirection.IDLE_RIGHT;
                }
                moveThread = null;
            }
        }
        g2.drawImage(animation.getCurrentFrame(currentDirection),0,0,null);

    }
}
