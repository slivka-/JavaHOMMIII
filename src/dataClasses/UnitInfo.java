package dataClasses;

import java.awt.Point;

import battleDisplay.GraphicsPanel;

/**
 * @author slivka
 * Klasa przechowywuj¹ca informacje o oddziale
 */
public class UnitInfo {
	
	public int unitID;
	public int unitSize;
	public UnitType unitType;
	public Point currentPos;
	public GraphicsPanel unitDisplay;
	
	public UnitInfo(int size, UnitType type, int ID)
	{
		this.unitSize = size;
		this.unitType = type;
		this.unitID = ID;
	}
}
