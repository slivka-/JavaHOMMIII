package Map;

import dataClasses.UnitType;

import java.util.ArrayList;

/**
 * Created by slivka on 10.02.2016.
 */
public class ReadyUnitTypes
{
    public static UnitType crusader = new UnitType("Rycerz","crusader","castle",10,10,1);
    public static UnitType halberdier = new UnitType("Halabardier","halberdier","castle",10,10,5);
    public static UnitType pikeman = new UnitType("Pikinier","pikeman","castle",10,10,1);
    public static UnitType swordsman = new UnitType("Miecznik","swordsman","castle",10,10,1);

    public static UnitType troglodyte = new UnitType("Troglodyta","troglodyte","dungeon",10,10,1);
    public static UnitType infernaltroglodyte = new UnitType("Piekielny troglodyta","infernaltroglodyte","dungeon",10,10,2);
    public static UnitType minotaur = new UnitType("Minotaur","minotaur","dungeon",10,10,1);
    public static UnitType minotaurking = new UnitType("Uberminotaur","minotaurking","dungeon",10,10,1);

    public static UnitType ogre = new UnitType("Ogr","ogre","stronghold",10,10,1);
    public static UnitType wolf = new UnitType("Wilk","wolf","stronghold",10,10,1);
    public static UnitType hobgoblin = new UnitType("Hobgoblin","hobgoblin","stronghold",10,10,1);
    public static UnitType behemoth = new UnitType("Behemot","behemoth","stronghold",10,10,1);

    public static ArrayList<UnitType> allUnits = new ArrayList<UnitType>(){{
        add(crusader);
        add(halberdier);
        add(pikeman);
        add(swordsman);
        add(troglodyte);
        add(infernaltroglodyte);
        add(minotaur);
        add(minotaurking);
        add(ogre);
        add(wolf);
        add(hobgoblin);
        add(behemoth);
    }};

    public static String[] getNames()
    {
        String[] names= new String[13];
        names[0] = "empty";
        for (int i=0;i<12;i++)
        {
            names[i+1] = allUnits.get(i)._name;
        }
        return names;
    }

    public static UnitType getUnitTypeByName(String name)
    {
        for(UnitType t : allUnits)
        {
            if(t._name.equals(name))
            {
                return t;
            }
        }
        return null;
    }
}
