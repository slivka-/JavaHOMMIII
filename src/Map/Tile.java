package Map;
import Map.MapObjects.MapObject;
import Map.MapObjects.TerrainPassability;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.Serializable;
import java.util.Map;

public class Tile extends JLayeredPane implements Serializable{

	private int cellWidth;
	private int cellHeight;
	private boolean isOccupied;
	private int movementCost;
	private JLabel background;
	private JLabel object;
	private int ID = -1;
	private MapObject mapObject;
	private TerrainPassability passability;
	private Point drawingPoint;
	private Point firstDrawingPoint;
	private Point centerPosition;

	public Tile(int cellWidth, int cellHeight, int movementCost, Image img, Point drawingPoint) {
		//super(new ImageIcon(img));
		this.setLayout(null);
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.movementCost = movementCost;
		this.setVisible(true);
		setBounds(0, 0, cellWidth, cellHeight);
		initializeBackground(img);
		initializeMapObject();
		isOccupied = false;
		this.firstDrawingPoint = drawingPoint;
		this.drawingPoint = drawingPoint;
	}

	public Tile(int cellWidth, int cellHeight, int movementCost, Image img, MapObject mapObject) {
		this.setLayout(null);
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.movementCost = movementCost;
		this.mapObject = mapObject;
		this.ID = mapObject.getID();
		this.setVisible(true);
		setBounds(0, 0, cellWidth, cellHeight);
		initializeBackground(img);
		initializeMapObject();
		isOccupied = true;
	}

	private void initializeBackground(Image img) {
		background = new JLabel(new ImageIcon(img));
		background.setOpaque(true);
		background.setPreferredSize(new Dimension(cellWidth, cellHeight));
		background.setBounds(0, 0, cellWidth, cellHeight);
		this.add(background, JLayeredPane.DEFAULT_LAYER);
	}

	private void initializeMapObject() {
		object = new JLabel();
		object.setOpaque(false);
		object.setPreferredSize(new Dimension(cellWidth, cellHeight));
		object.setBounds(0, 0, cellWidth, cellHeight);
		this.add(object, JLayeredPane.PALETTE_LAYER);
	}

	public void setMapObject(ImageIcon img) {
		object.setIcon(img);
		object.setVisible(true);
		object.revalidate();
		object.repaint();
		object.updateUI();
		this.revalidate();
		this.repaint();
		this.updateUI();
	}

	public MapObject getMapObject()
	{
		return this.mapObject;
	}

	public void setMapObject(MapObject mo, Point drawingPoint) {
		this.mapObject = mo;
		this.ID = mo.getID();
		this.drawingPoint = drawingPoint;
		object.setVisible(true);
		object.revalidate();
		object.repaint();
		object.updateUI();
		this.revalidate();
		this.repaint();
		this.updateUI();
	}

	public void setMapObjectBorder(Border b) {
		object.setBorder(b);
	}

	public boolean getOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean occupation) {
		this.isOccupied = occupation;
	}

	public int getMovementCost() {
		return movementCost;
	}

	public void printInfo() {
		if (mapObject != null) {
			mapObject.printInfo();
		}
		else {
			System.out.println("Podono jest nullem");
		}
	}

	public int getID() {
		return ID;
	}

	public void deleteMapObject() {
		this.ID = -1;
		this.setOccupied(false);
		drawingPoint = firstDrawingPoint;
		movementCost = 1;
		object.setIcon(null);
		object.revalidate();
	}
		
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(cellWidth, cellHeight);
	}

	public Point getDrawingPoint() {
		return drawingPoint;
	}

	public Point getCenterPosition()
	{
		return centerPosition;
	}

	public void setCenterPosition(Point centerPosition)
	{
		this.centerPosition = centerPosition;
	}
}
