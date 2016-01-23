package Map.MapObjects.Towns;

import java.awt.image.BufferedImage;

public class Zamek extends Town {

    public Zamek(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
    }
}
