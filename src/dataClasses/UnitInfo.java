package dataClasses;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import battleDisplay.GraphicsPanel;

/**
 * @author slivka
 * Klasa przechowywuj�ca informacje o oddziale
 */
public class UnitInfo implements Serializable{

	public int unitSize;
	public UnitType unitType;
	public Point currentPos;
	public int woundedUnitHealth;
	public int commander;
	private GraphicsPanel unitDisplay;
	public int Defending;



	public UnitInfo(int size, UnitType type, int commander)
	{
		this.unitSize = size;
		this.unitType = type;
		this.commander = commander;
		this.woundedUnitHealth = 0;
		this.Defending = 1;

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
		this.Defending = 1;
		unitDisplay.setAsActive();
	}

	public void setAsNotActive()
	{
		unitDisplay.setAsNotActive();
	}

	public void setDefending()
	{
		this.Defending = 2;
	}

	public void updateUnitSize(int newUnitSize)
	{
		unitSize = newUnitSize;
		unitDisplay.updateUnitSize(unitSize);
	}

	public void moveUnit(ArrayList<Point> path)
	{
		unitDisplay.movePanel(path);
	}

	public void attackUnit(ArrayList<Point> path)
	{
		unitDisplay.attackUnit(path);
	}

	public void unitDeath()
	{
		unitDisplay.unitDeath();
	}

	public boolean isAnimationFinished()
	{
		return unitDisplay.isAnimationFinished();
	}
}
