package dataClasses;

import java.util.HashMap;
import java.util.TreeMap;


/**
 * @author slivka
 * Klasa zawiera dane nt bohatera i jego armii
 */
public class HeroInfo {
	private int ID;
	private String Name;
	private int ATK;
	private int DEF;
	private HashMap<Integer, UnitInfo> army;
	
	public HeroInfo()
	{
		ID = 0;
		Name = "Default";
		ATK = 1;
		DEF = 1;
		setArmy(new HashMap<Integer, UnitInfo>());
	}
	
	/**
	 * Dodaje jednostki do armii bohatera
	 * @param position pozycja w armii(1-5)
	 * @param unit oddzia³
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
	
	
}
