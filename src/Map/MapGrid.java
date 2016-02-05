package Map;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import GraphicsProcessing.Graphics;
import GraphicsProcessing.ImageProcessor;
import ImageSelection.ImageSelectionBox;
import Map.MapObjects.MapObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


public class MapGrid extends JPanel{
	
	private int rowCount;
	private int colCount;
	private final int cellWidth = 32;
	private final int cellHeight = 32;
	private Tile[][] cells;
	private boolean isGridOn = false;
	private Graphics graphics;
	private ImageSelectionBox isb;

	public MapGrid(Graphics g, ImageSelectionBox isb){
		this.graphics = g;
		this.isb = isb;
		setLayout(new GridBagLayout());
	}
	
	public void initializeGrid(MapSize size) {
		removeAll();
		isGridOn = false;
		rowCount = colCount = size.getValue();
		cells = new Tile[colCount][rowCount];
		GridBagConstraints gbc = new GridBagConstraints();	
		for (int col = 0; col < colCount; ++col) {
			for  (int row = 0; row < rowCount; ++row){
				gbc.gridx = col;
				gbc.gridy = row;
				
				Image img = graphics.getRandomTileDefaultBackgroundImage();
				//movementCost temporary set to 1- it should be set depending on background
				Tile cell = new Tile(cellWidth, cellHeight, 1, img, new Point(col, row));
				add(cell, gbc);
				cells[col][row] = cell;
			}
		}
		updateUI();
	}

	public void readSavedMap(SavedMap savedMap)
	{
		this.initializeGrid(savedMap.get_mapSize());
		ArrayList<Point> usedPoints = new ArrayList<Point>();
		this.cells = savedMap.get_cells();

		for(Tile[] TileRow:cells)
		{
			for (Tile t:TileRow)
			{
				if(t.getCenterPosition()!= null)
				{
					int centerX = t.getCenterPosition().x;
					int centerY = t.getCenterPosition().y;
					boolean draw = true;
					for (Point p : usedPoints)
					{
						if (p.x == centerX && p.y == centerY)
						{
							draw = false;
						}
					}
					if (draw)
					{
						usedPoints.add(t.getCenterPosition());
						t.printInfo();
						System.out.println(t.getMapObject().getImage().toString());
						BufferedImage chunks[] = ImageProcessor.divideImage(t.getMapObject().getImage(), cellWidth, cellHeight);
						int rows = t.getMapObject().getImage().getHeight(null) / cellHeight;
						int cols = t.getMapObject().getImage().getWidth(null) / cellWidth;
						drawImageOnTiles(chunks, rows, cols, centerX, centerY, t.getMapObject());
					}
				}
				t.updateUI();
				t.revalidate();
				t.repaint();
			}
		}
		this.revalidate();
		this.repaint();
	}

	public MapSize getMapSize()
	{
		if(rowCount == MapSize.SMALL.getValue())
		{
			return MapSize.SMALL;
		}
		else if(rowCount == MapSize.MEDIUM.getValue())
		{
			return MapSize.MEDIUM;
		}
		else
		{
			return MapSize.LARGE;
		}
	}
/*
	public void initializeGrid() {
		removeAll();
		isGridOn = false;
		rowCount = colCount = 32;
		cells2 = new Tile2[colCount][rowCount];
		for (int col = 0; col < colCount; ++col) {
			for (int row = 0; row < rowCount; ++row) {
				Tile2 cell = new Tile2(cellWidth, cellHeight, 1);
				Image img = graphics.getRandomTileDefaultBackgroundImage();
				cell.setImage(img);
				cell.setID(-1);
				cells2[col][row] = cell;
			}
		}
		/*
		removeAll();
		isGridOn = false;
		rowCount = colCount = 32;

		for (int col = 0; col < colCount; ++col) {
			for (int row = 0; row < rowCount; ++row) {
				Image img = graphics.getRandomTileDefaultBackgroundImage();
				//Graphics2D g;
				java.awt.Graphics g = getComponentGraphics(img.getGraphics());
				g.drawImage(img, col * cellWidth, row * cellHeight, null);
				System.out.println(col + " : " + row);
			}
		}
		updateUI();
	}
	*/
/*
	protected void paintComponent(java.awt.Graphics g) {
		removeAll();
		isGridOn = false;
		rowCount = colCount = 10;

		for (int col = 0; col < colCount; ++col) {
			for (int row = 0; row < rowCount; ++row) {
				//Image img = graphics.getRandomTileDefaultBackgroundImage();
				Image img = cells2[col][row].getImage();
				//Graphics2D g;

				//g.drawImage(img, col * cellWidth, row * cellHeight, null);
				drawTile(g, img, col * cellWidth, row * cellHeight);
				System.out.println(col + " : " + row);
			}
		}
		System.out.println("wowwowwowwowwowwowwow");
	}

	protected void drawTile(java.awt.Graphics g, Image img, int x, int y) {
		g.drawImage(img, x, y, null);
	}
*/
	/**
	 * If element is selected in Image Selection Box and you click on the map
	 * it changes the image (icon) of given tile. Note: to have more images
	 * we need for example JPanel with more than 1 JLabel to represent a tile.
	 * Each label holds different image.
	 * @param x mouse X
	 * @param y mouse Y
	 */
	public void changeCellImage(int x, int y) {
		//powinno być jakieś sprawdzanie jaki to object
		//get object
		String category = isb.getSelectedCategory();
		String name = isb.getSelectedImageName();
		if (category == null || name == null) {
			System.out.println("Brak obrazka");
			return;
		}
		else {
			BufferedImage newImg = isb.getSelectedImage();
			MapObject mo = MapObject.makeMapObject(category, name, newImg);
			//Image newImg = mo.getImage();
			System.out.println("Nazwa obrazka: " + isb.getSelectedImageName());
			BufferedImage chunks[] = ImageProcessor.divideImage(newImg, cellWidth, cellHeight);

			int rows = newImg.getHeight(null) / cellHeight;
			int cols = newImg.getWidth(null) / cellWidth;
			int centerX = x / cellWidth;
			int centerY = y / cellHeight;

			if(!isImageWithinBonds(rows, cols, centerX, centerY))
				return;

			if(areTilesOccupied(rows, cols, centerX, centerY))
				return;

			drawImageOnTiles(chunks, rows, cols, centerX, centerY, mo);
		}
	}

	public void printTileID(int x, int y) {
		System.out.println("Jednak dziala");
		int mouseX = x / cellHeight;
		int mouseY = y / cellWidth;
		System.out.println("x: " + x + "  y: " + y);
		System.out.println("x: " + mouseX + "  y: " + mouseY);
		System.out.println("Occupied? " + cells[mouseX][mouseY].getOccupied());
		System.out.println(cells[mouseX][mouseY].getID());
		cells[mouseX][mouseY].printInfo();
	}

	public void deleteTile(int x, int y) {
		int mouseX = x / cellHeight;
		int mouseY = y / cellWidth;
		int ID = cells[mouseX][mouseY].getID();
		//need better one
		for (int i = 0; i < colCount; ++i) {
			for (int j = 0; j < rowCount; ++j) {
				if (cells[i][j].getID() == ID) {
					cells[i][j].deleteMapObject();
				}
			}
		}
	}

	private boolean areTilesOccupied(int rows, int cols, int centerX, int centerY) {
		// Check if any tile within range is occupied.
		// If it is then don't draw.
		for (int r = 0; r < rows; ++r) {
			for(int c = 0; c < cols; ++c) {
				int _x = centerX + c - cols/2;
				int _y = centerY + r - rows+1;
				//TODO: check if tile is occupied
				if (cells[_x][_y].getOccupied())
				{
					return true;
				}
			}
		}
		return false;
	}

	private boolean isImageWithinBonds(int rows, int cols, int centerX, int centerY) {
		//left upper corner -> right upper corner -> left down corner -> right down corner
		//must be within map
		if (centerX - cols/2 < 0 || centerX + cols/2 > colCount || centerY - rows + 1 < 0)
		{
			return false;
		}
		return true;
	}

	private void drawImageOnTiles(BufferedImage chunks[], int rows, int cols, int centerX, int centerY, MapObject mo) {
		int count = 0;
		for (int r = 0; r < rows; ++r) {
			for(int c = 0; c < cols; ++c) {
				int _x = centerX + c - cols/2;
				int _y = centerY + r - rows+1;
				cells[_x][_y].setMapObject(new ImageIcon(chunks[count++]));
				cells[_x][_y].setOccupied(true);
				cells[_x][_y].setMapObject(mo, new Point(_x, _y));
				cells[_x][_y].setCenterPosition(new Point(centerX,centerY));
				//cells[x][y].setType = town/mine/etc.
				//cells[x][y].canMove = false //exception- center(true)
				//or collect cells and put them in collection of object of i.e. town type
			}
		}
		cells[centerX][centerY].setOccupied(false);
	}



	public void showHideGrid() {
		if (isGridOn) {
			hideGrid();
			isGridOn = false;
		}
		else {
			drawGrid();
			isGridOn = true;
		}
	}

	public Tile[][] getMapCells(){return this.cells;}

	private void drawGrid() {
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < colCount; ++col) {
				
				MatteBorder b = null;
				
				if (row < rowCount) {
					if (col < colCount) {
						b = new MatteBorder(1, 1, 0, 0, Color.black);
					}
					else {
						b = new MatteBorder(1, 1, 0, 1, Color.black);
					}
				}
				else {
					if (col < colCount) {
						b = new MatteBorder(1, 1, 1, 0, Color.black);
					}
					else {
						b = new MatteBorder(1, 1, 1, 1, Color.black);
					}
				}
				cells[row][col].setMapObjectBorder(b);
			}
		}
	}
	
	private void hideGrid() {
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < colCount; ++col) {
				cells[row][col].setMapObjectBorder(BorderFactory.createEmptyBorder());
			}
		}
	}

}
