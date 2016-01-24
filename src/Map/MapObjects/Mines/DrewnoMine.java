package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;

public class DrewnoMine extends Mine {

    public DrewnoMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.DREWNO;
    }
}
