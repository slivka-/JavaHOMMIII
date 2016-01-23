package dataClasses;

import java.awt.Point;

public class BattlefieldCell {

	public CellEntity contains;
	public UnitInfo unit;
	public String imgPath;
	public Point drawingPoint;
	public Point location;

	public BattlefieldCell(int cellX, int cellY, Point location)
	{
		contains = CellEntity.EMPTY;
		unit = null;
		imgPath = null;
		drawingPoint = new Point(cellX, cellY);
		this.location = location;
	}
}
