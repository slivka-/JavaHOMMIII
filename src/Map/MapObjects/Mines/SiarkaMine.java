package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;

public class SiarkaMine extends Mine{

    public SiarkaMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.SIARKA;
    }
}
