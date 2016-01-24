package Map.MapObjects.Resources;

import Map.MapObjects.Mines.ResoruceType;

import java.awt.image.BufferedImage;

public class Ruda extends Resource {

    public Ruda(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.RUDA;
    }
}
