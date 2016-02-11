package dataClasses;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by slivka on 11.02.2016.
 */
public class MiniHeroInfo implements Serializable
{
    public int ID;
    public String Name;
    public HashMap<Integer, UnitInfo> army;
    public Point currentPosition;
    public Point townPosition;

    public MiniHeroInfo(int ID,String Name, HashMap<Integer, UnitInfo> army, Point currentPosition, Point townPosition)
    {
        this.ID = ID;
        this.Name = Name;
        this.army = army;
    }

    public HashMap<Integer, UnitInfo> getArmy() {
        return army;
    }

    public void removeFromArmy(UnitInfo unit)
    {
        Iterator<Integer> iterator = army.keySet().iterator();
        while(iterator.hasNext())
        {
            Integer key = iterator.next();
            if(army.get(key).equals(unit))
            {
                army.remove(key);
                break;
            }
        }
    }
}
