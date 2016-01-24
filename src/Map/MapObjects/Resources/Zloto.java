package Map.MapObjects.Resources;

import Map.MapObjects.Mines.ResoruceType;

import java.awt.image.BufferedImage;

public class Zloto extends Resource {

    public Zloto(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.ZLOTO;
    }
}
