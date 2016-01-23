package Map.MapObjects;


import Map.MapObjects.Mines.Mine;
import Map.MapObjects.Resources.Resource;
import Map.MapObjects.Towns.Town;

import java.awt.image.BufferedImage;

public class MapObject {
    protected static int availableID = 0;

    //protected Tile[] tiles;
    protected int ID;
    protected BufferedImage img;
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

    public static MapObject makeMapObject(String category, String object, BufferedImage img) {
        MapObject mapObject = null;

        if(category.equals("towns")) {
            mapObject = Town.makeTown(object, img);
        }
        else if(category.equals("mines")) {
            mapObject = Mine.makeMine(object, img);
        }
        else if(category.equals("miscellaneous")) {
            mapObject = Miscellaneous.makeMiscellaneous(object, img);
        }
        else if(category.equals("resources")) {
            mapObject = Resource.makeResource(object, img);
        }

        return mapObject;
    }
}
