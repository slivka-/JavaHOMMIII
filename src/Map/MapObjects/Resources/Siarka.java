package Map.MapObjects.Resources;

import Map.MapObjects.Mines.ResoruceType;

import java.awt.image.BufferedImage;

public class Siarka extends Resource{

    public Siarka(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.SIARKA;
    }
}
