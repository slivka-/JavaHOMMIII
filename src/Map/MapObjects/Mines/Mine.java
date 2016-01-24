package Map.MapObjects.Mines;

import GraphicsProcessing.Graphics;
import Map.MapObjects.MapObject;

import java.awt.image.BufferedImage;
import java.io.File;

public class Mine extends MapObject {

    protected ResoruceType resoruceType;
    protected int amountPerDay = 1;

    /***
     * Returns type of resource that mine products
     * @return
     */
    public ResoruceType getResoruceType() {
        return resoruceType;
    }

    /***
     * Returns daily growth of given resource
     * @return
     */
    public int getAmountPerDay() {
        return amountPerDay;
    }

    public static Mine makeMine(String name, BufferedImage img) {
        Mine m = null;

        if (name.equals("rtecMine.png")) {
            m = new RtecMine(img);
        }
        else if (name.equals("siarkaMine.png")) {
            m = new SiarkaMine(img);
        }
        else if (name.equals("klejnotMine.png")) {
            m = new KlejnotMine(img);
        }
        else if (name.equals("drewnoMine.png")) {
            m = new DrewnoMine(img);
        }
        else if (name.equals("rudaMine.png")) {
            m = new RudaMine(img);
        }
        else if (name.equals("zlotoMine.png")) {
            m = new ZlotoMine(img);
        }
        else if (name.equals("krysztalMine.png")) {
            m = new KrysztalMine(img);
        }
        return m;
    }
}
