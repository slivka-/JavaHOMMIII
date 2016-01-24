package Map.MapObjects.Resources;

import Map.MapObjects.Mines.ResoruceType;

import java.awt.image.BufferedImage;

public class Drewno extends Resource {

    public Drewno(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.DREWNO;
    }
}
