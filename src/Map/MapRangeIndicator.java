package Map;

import Map.MapObjects.TerrainPassability;
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
    public TerrainPassability passability;
    //TODO: dodac oznaczenie na czym jest ten wskaznik(puste miejsce, oddzial, przeciwnik, zasoby, itp)

    public MapRangeIndicator(Point p, MouseListener mListener, TerrainPassability passability)
    {
        this.setOpaque(false);
        this.setBounds(p.x * 32, p.y * 32, 32, 32);
        this.location = p;
        this.passability = passability;
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
