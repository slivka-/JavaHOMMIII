package Map.MapObjects.Resources;

import Map.MapObjects.MapObject;
import Map.MapObjects.Mines.ResoruceType;

import java.awt.image.BufferedImage;

public class Resource extends MapObject {

    protected int amount;

    protected ResoruceType resoruceType;

    public int getAmount() {
        return amount;
    }

    public ResoruceType getResoruceType() {
        return resoruceType;
    }

    public static Resource makeResource(String name, BufferedImage img) {
        Resource r = null;

        if (name.equals("rtec.png")) {
            r = new Rtec(img);
        }
        else if (name.equals("siarka.png")) {
            r = new Siarka(img);
        }
        else if (name.equals("klejnot.png")) {
            r = new Klejnot(img);
        }
        else if (name.equals("drewno.png")) {
            r = new Drewno(img);
        }
        else if (name.equals("ruda.png")) {
            r = new Ruda(img);
        }
        else if (name.equals("zloto.png")) {
            r = new Zloto(img);
        }
        else if (name.equals("krysztal.png")) {
            r = new Krysztal(img);
        }

        return r;
    }
}
