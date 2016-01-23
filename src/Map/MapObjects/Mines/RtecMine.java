package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;

public class RtecMine extends Mine {

    public RtecMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.RTEC;
    }
}
