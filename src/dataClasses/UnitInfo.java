package dataClasses;

import java.awt.Point;

import battleDisplay.GraphicsPanel;

/**
 * @author slivka
 * Klasa przechowywuj�ca informacje o oddziale
 */
public class UnitInfo {
	
	public int unitID;
	public int unitSize;
	public UnitType unitType;
	public Point currentPos;
	private GraphicsPanel unitDisplay;


	public UnitInfo(int size, UnitType type, int ID)
	{
		this.unitSize = size;
		this.unitType = type;
		this.unitID = ID;
	}

	public void setUnitDisplay(Point drawingPoint)
	{
		unitDisplay = new GraphicsPanel(this.unitType,drawingPoint.x,drawingPoint.y,this.unitSize);
		unitDisplay.setOpaque(false);
	}

	public GraphicsPanel getUnitDisplay()
	{
		return unitDisplay;
	}

	public void flipFacing()
	{
		unitDisplay.flipFacing();
	}

	public void setAsActive()
	{
		unitDisplay.setAsActive();
	}

	public void setAsNotActive()
	{
		unitDisplay.setAsNotActive();
	}

	public void updateUnitSize(int newUnitSize)
	{
		unitDisplay.updateUnitSize(newUnitSize);
	}

	public void moveUnit(Point p)
	{
		unitDisplay.movePanel(p);
	}
}
