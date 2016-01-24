package Map.MapObjects.Towns;

import java.awt.image.BufferedImage;

public class Loch extends Town {

    public Loch(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
    }
}
