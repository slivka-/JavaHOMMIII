package Map.MapObjects.Towns;

import java.awt.image.BufferedImage;

public class Forteca extends Town{

    public Forteca(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
    }
}
