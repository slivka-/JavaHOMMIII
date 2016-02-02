package battleDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by slivka on 23.01.2016.
 */
public class RangeIndicator extends JPanel
{
    public Point location;

    public AttackDirection attackDirection;


    public RangeIndicator(Point p, MouseListener mListener, MouseMotionListener mMotionListener)
    {
        this.setOpaque(false);
        this.setBounds(p.x+1,p.y+1,50,43);
        this.location = new Point((p.x-50)/50,((p.y-110)/43));
        this.attackDirection = AttackDirection.RIGHT;
        if(mListener != null)
        {
            this.addMouseListener(mListener);
        }
        if(mMotionListener != null)
        {
            this.addMouseMotionListener(mMotionListener);
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, 50, 43);
    }
}
