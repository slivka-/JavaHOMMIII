package Map.MapObjects.Towns;

import GraphicsProcessing.Graphics;
import Map.MapObjects.MapObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Town extends MapObject {
    //albo String lochName = "loch"
    //i w Graphics zrobić coś w stylu Dictionary<String, Dictionary<String, Image>>
    //gdzie pierwszy string to nazwa folderu, drugi to nazwa pliku
    //i po prostu tak pobierać poprzez town -> loch i mamy obrazek lochu
    //gorzej by było z animacją, wtedy np. Image[]

    public static Town makeTown(String name, BufferedImage g) {
        Town t = null;

        if (name.equals("loch.png")) {
            t = new Loch(g);
            return t;
        }
        else if (name.equals("zamek.png")) {
            t = new Zamek(g);
            return t;
        }
        else if (name.equals("forteca.png")) {
            t = new Forteca(g);
            return t;
        }
        return t;
    }

}
