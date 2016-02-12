package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class KlejnotMine extends Mine {

    public KlejnotMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.KLEJNOT;
    }

    @Override
    public HashMap<String, Integer> objectAction()
    {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("Klejnot",50);
        return m;
    }
}
