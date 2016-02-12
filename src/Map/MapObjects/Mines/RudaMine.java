package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class RudaMine extends Mine {

    public RudaMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.RUDA;
    }

    @Override
    public HashMap<String, Integer> objectAction()
    {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("Ruda",50);
        return m;
    }
}
