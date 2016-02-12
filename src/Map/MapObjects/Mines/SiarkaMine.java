package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SiarkaMine extends Mine{

    public SiarkaMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.SIARKA;
    }

    @Override
    public HashMap<String, Integer> objectAction()
    {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("Siarka",50);
        return m;
    }
}
