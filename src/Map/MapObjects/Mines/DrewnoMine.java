package Map.MapObjects.Mines;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class DrewnoMine extends Mine {

    public DrewnoMine(BufferedImage img) {
        this.img = img;
        this.ID = super.generateID();
        this.resoruceType = ResoruceType.DREWNO;
    }

    @Override
    public HashMap<String, Integer> objectAction()
    {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("Drewno",50);
        return m;
    }
}
