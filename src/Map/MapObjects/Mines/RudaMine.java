package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;

public class RudaMine extends Mine {

    public RudaMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.RUDA;
    }
}
