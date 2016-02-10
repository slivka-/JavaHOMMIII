package Map.MapObjects;

import dataClasses.UnitInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class Army extends MapObject {

    public HashMap<Integer, UnitInfo> army;
    private Point currentLocation;

    public Army(HashMap<Integer, UnitInfo> armyUnits)
    {
        this.army = armyUnits;
        try
        {
            this.img = ImageIO.read(new File("assets\\units\\armyMini.png"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void setLocation(Point location)
    {
        this.currentLocation = location;
    }
}
