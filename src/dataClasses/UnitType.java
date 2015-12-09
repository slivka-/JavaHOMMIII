package dataClasses;

import java.util.Random;

/**
 * @author slivka
 * Klasa przechowywuj¹ca informacje o jednostce
 */
public class UnitType {
	public String name;
	public String spritepath;
	public int initiative;
	
	public UnitType(String name)
	{
		this.name = name;
		this.spritepath = "assets\\units\\p1.png";
		Random rnd = new Random();
		this.initiative = rnd.nextInt(10);
	}
}
