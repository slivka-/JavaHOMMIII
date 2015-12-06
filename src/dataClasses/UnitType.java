package dataClasses;

/**
 * @author slivka
 * Klasa przechowywująca informacje o jednostce
 */
public class UnitType {
	public String name;
	public String spritepath;
	
	public UnitType(String name)
	{
		this.name = name;
		this.spritepath = "assets\\units\\p1.png";
	}
}
