package dataClasses;

import HeroDisplay.HeroDisplayPanel;
import Map.MapObjects.Towns.Town;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;


/**
 * @author slivka
 * Klasa zawiera dane nt bohatera i jego armii
 */
public class HeroInfo implements Serializable{
	private int ID;
	public String Name;
	public Town homeTown;
	public Point currentPosition;
	public Point townPosition;
	public HeroDisplayPanel heroDisplay;
	public int heroRange = 8;

	public HashMap<String,Integer> resources;//gold, wood, ore, itp
	private HashMap<Integer, UnitInfo> army;
	
	public HeroInfo(String name,Town town)
	{
		ID = 0;
		Name = name;
		homeTown = town;
		setArmy(new HashMap<Integer, UnitInfo>());
	}
	
	/**
	 * Dodaje jednostki do armii bohatera
	 * @param position pozycja w armii(1-5)
	 * @param unit oddzia�
	 */
	public void addToArmy(int position, UnitInfo unit)
	{
		if(army.size()<5)
		{
			army.put(position, unit);
		}
		army = new HashMap<Integer, UnitInfo>(new TreeMap<Integer, UnitInfo>(army));
	}
	
	public HashMap<Integer, UnitInfo> getArmy() {
		return army;
	}

	public void setArmy(HashMap<Integer, UnitInfo> hashMap) {
		this.army = hashMap;
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

	public void backToHomeTown()
	{
		heroDisplay.setBounds((townPosition.x*32)-32,(townPosition.y*32)-32,96,64);
	}
}
