package Map.MapObjects.Landscapes;

import Map.MapObjects.MapObject;

import java.awt.image.BufferedImage;

public class Landscape extends MapObject{

    public Landscape(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
    }

    public static Landscape makeLandscape(BufferedImage img) {
        Landscape l = new Landscape(img);
        return l;
    }
}
