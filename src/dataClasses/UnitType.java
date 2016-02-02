package dataClasses;

import java.util.Random;

/**
 * @author slivka
 * Klasa przechowywuj�ca informacje o jednostce
 */
public class UnitType {
	public String _name;
	public String _spriteName;
	public String _townName;

	//STATS
	public int _health;
	public int _attack;
	public int initiative;
	public int speed = 20;

	
	public UnitType(String name, String spriteName,String townName,int attack,int health)
	{
		this._name = name;
		this._spriteName = spriteName;
		this._townName = townName;
		this._attack = attack;
		this._health = health;
		Random rnd = new Random();
		this.initiative = rnd.nextInt(10);
	}
}
