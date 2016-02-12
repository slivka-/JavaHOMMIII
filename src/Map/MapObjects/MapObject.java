package Map.MapObjects;


import Map.MapObjects.Landscapes.Landscape;
import Map.MapObjects.Mines.Mine;
import Map.MapObjects.Miscellaneous.Misc;
import Map.MapObjects.Resources.Resource;
import Map.MapObjects.Towns.Town;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class MapObject implements Serializable{
    protected static int availableID = 0;

    //protected Tile[] tiles;
    protected int ID;
    protected transient BufferedImage img;
    protected TerrainPassability passability;
    //Player ownership

    protected static int generateID() {
        return availableID++;
    }

    public MapObject() {}

    public int getID() {
        return ID;
    }

    public BufferedImage getImage() {
        return img;
    }

    public void printInfo() {
        System.out.println("ID: " + ID);
        System.out.println("ObjectName: " + this.getClass().getName());
    }

    public TerrainPassability getPassability() {
        return passability;
    }

    public HashMap<String, Integer> objectAction(){return null;}

    public static MapObject makeMapObject(String category, String object, BufferedImage img) {
        MapObject mapObject = null;

        if(category.equals("towns")) {
            mapObject = Town.makeTown(object, img);
        }
        else if(category.equals("mines")) {
            mapObject = Mine.makeMine(object, img);
        }
        else if(category.equals("miscellaneous")) {
            mapObject = Misc.makeMisc(object, img);
        }
        else if(category.equals("resources")) {
            mapObject = Resource.makeResource(object, img);
        }
        else if(category.equals("landscapes")) {
            mapObject = Landscape.makeLandscape(img);
        }

        return mapObject;
    }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.defaultWriteObject();
        ImageIO.write(img,"png",out);
    }

    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException
    {
        in.defaultReadObject();
        img = ImageIO.read(in);
    }
}
