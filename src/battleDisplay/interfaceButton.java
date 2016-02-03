package battleDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by slivka on 02.02.2016.
 */
public class interfaceButton extends JPanel
{
    private BufferedImage _img;

    public interfaceButton(String src, MouseListener listener)
    {
        try
        {
            this._img = ImageIO.read(new File(src));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        this.addMouseListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(_img,0,0,null);
    }
}
