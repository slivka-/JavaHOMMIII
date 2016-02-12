package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class KrysztalMine extends Mine{

    public KrysztalMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.KRYSZTAL;
    }

    @Override
    public HashMap<String, Integer> objectAction()
    {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("Krysztal",50);
        return m;
    }
}
