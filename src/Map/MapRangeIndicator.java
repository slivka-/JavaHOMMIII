package Map;

import battleDisplay.AttackDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by slivka on 23.01.2016.
 */
public class MapRangeIndicator extends JPanel
{
    public Point location;
    //TODO: dodac oznaczenie na czym jest ten wskaznik(puste miejsce, oddzial, przeciwnik, zasoby, itp)

    public MapRangeIndicator(Point p, MouseListener mListener)
    {
        this.setOpaque(false);
        this.setBounds(p.x * 32, p.y * 32, 32, 32);
        System.out.println(p);
        this.location = p;
        if(mListener != null)
        {
            this.addMouseListener(mListener);
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, 50, 43);
    }
}
