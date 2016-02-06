package HeroDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by slivka on 06.02.2016.
 */
public class HeroDisplayPanel extends JPanel
{

    private ExecutorService ex;
    private HeroAnimationThread animation;
    private HeroDirection currentDirection;

    public HeroDisplayPanel(int HeroID)
    {
        //this.setLayout(null);
        this.currentDirection = HeroDirection.MOVING_LEFT;
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

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(animation.getCurrentFrame(currentDirection),0,0,null);
    }
}
