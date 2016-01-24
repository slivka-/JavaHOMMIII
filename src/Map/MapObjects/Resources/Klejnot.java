package Map.MapObjects.Resources;

import Map.MapObjects.Mines.ResoruceType;

import java.awt.image.BufferedImage;

public class Klejnot extends Resource {

    public Klejnot(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.KLEJNOT;
    }
}
