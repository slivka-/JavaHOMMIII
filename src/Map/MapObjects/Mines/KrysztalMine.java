package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;

public class KrysztalMine extends Mine{

    public KrysztalMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.KRYSZTAL;
    }
}
