package dataClasses;

import java.io.Serializable;
import java.util.Random;

/**
 * @author slivka
 * Klasa przechowywuj�ca informacje o jednostce
 */
public class UnitType implements Serializable{
	public String _name;
	public String _spriteName;
	public String _townName;

	//STATS
	public int _health;
	public int _attack;
	public int _initiative;
	public int speed = 5;

	
	public UnitType(String name, String spriteName,String townName,int attack,int health, int initiative)
	{
		this._name = name;
		this._spriteName = spriteName;
		this._townName = townName;
		this._attack = attack;
		this._health = health;
		this._initiative = initiative;
	}
}
