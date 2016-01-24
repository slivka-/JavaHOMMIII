package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;

public class KlejnotMine extends Mine {

    public KlejnotMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.KLEJNOT;
    }
}
