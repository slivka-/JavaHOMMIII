package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;

public class ZlotoMine extends Mine {

    public ZlotoMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.ZLOTO;
    }
}
