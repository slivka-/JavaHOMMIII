package battleDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by slivka on 21.01.2016.
 */
public class StaticGraphicsPanel extends JPanel
{
    private static final long serialVersionUID = 3688560623253859696L;

    private int _x;
    private int _y;
    private BufferedImage image;



    public StaticGraphicsPanel(String path, int x, int y)
    {
        try
        {
            image = ImageIO.read(new File(path));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        this._x = x;
        this._y = y;

        this.setBounds(_x-(image.getWidth()/2),_y-image.getHeight(), image.getWidth(), image.getHeight());

    }


    @Override
    protected void paintComponent(Graphics g)
    {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, 0, 0, null);
    }
}
