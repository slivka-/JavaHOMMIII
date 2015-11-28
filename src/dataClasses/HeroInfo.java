package dataClasses;

import java.util.HashMap;
import java.util.TreeMap;


//Klasa zawieraj¹ca dane bohatera i jego armie;
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
	
	public void addToArmy(int position, UnitInfo unit)
	{
		if(army.size()<5)
		{
			army.put(position, unit);
		}
		army = new HashMap(new TreeMap(army));
	}
	
	public HashMap<Integer, UnitInfo> getArmy() {
		return army;
	}

	public void setArmy(HashMap<Integer, UnitInfo> hashMap) {
		this.army = hashMap;
	}
	
	
}
