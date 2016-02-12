package Map;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import GraphicsProcessing.Graphics;
import GraphicsProcessing.ImageProcessor;
import HeroDisplay.HeroDisplayPanel;
import ImageSelection.ImageSelectionBox;
import Map.MapObjects.Army;
import Map.MapObjects.MapObject;
import Map.MapObjects.TerrainPassability;
import Map.MapObjects.Towns.Loch;
import Map.MapObjects.Towns.Town;
import Pathfinding.Pathfinder;
import dataClasses.HeroInfo;
import mapLogic.MapGameController;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;


public class MapGrid extends JLayeredPane{
	
	private int rowCount;
	private int colCount;
	private final int cellWidth = 32;
	private final int cellHeight = 32;
	public Tile[][] cells;
	private boolean isGridOn = false;
	private Graphics graphics;
	private ImageSelectionBox isb;

	private MouseListener listener;

	private MapGameController controller;


	/**
	 * Constructor for editor mode
	 * @param g
	 * @param isb
	 */
	public MapGrid(Graphics g, ImageSelectionBox isb){
		this.graphics = g;
		this.isb = isb;
		//setLayout(new GridBagLayout());
		setLayout(null);
		this.listener = null;
		this.controller = null;

	}

	/**
	 * Constructor for game mode
	 * @param g
	 */
	public MapGrid(Graphics g, MapGameController c)
	{
		this.graphics = g;
		this.isb = null;
		this.controller = c;
		setLayout(null);
		this.listener = new MouseListener()
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image moveImage = toolkit.getImage("assets/cursors/mapMove.png");
			Image battleImage = toolkit.getImage("assets/cursors/mapBattle.png");
			Image actionImage = toolkit.getImage("assets/cursors/mapAction.png");

			Cursor moveCursor = toolkit.createCustomCursor(moveImage,new Point(12,12),"img");
			Cursor battleCursor = toolkit.createCustomCursor(battleImage,new Point(12,12),"img");
			Cursor actionCursor = toolkit.createCustomCursor(actionImage,new Point(12,12),"img");
			@Override
			public void mouseClicked(MouseEvent e)
			{
				MapRangeIndicator t = (MapRangeIndicator)e.getSource();
				switch (t.passability)
				{
					case UNOCCUPIED:
						controller.moveHeroSend(t.location);
						break;
					case ARMY:
						controller.AttackUnitSend(t.location, (Army) cells[t.getX() / 32][t.getY() / 32].getMapObject());
						break;
					case HERO:
						controller.AttackUnitSend(t.location,controller.getHeroByPoint(new Point(t.getX()/32,t.getY()/32)).toMiniHeroInfo());
						break;
					case COLLECTABLE:
						controller.CollectibleSend(t.location, cells[t.getX() / 32][t.getY() / 32].getMapObject());
						break;
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				MapRangeIndicator t = (MapRangeIndicator)e.getSource();
				switch (t.passability)
				{
					case UNOCCUPIED:
						setCursor(moveCursor);
						break;
					case ARMY:
						setCursor(battleCursor);
						break;
					case HERO:
						setCursor(battleCursor);
						break;
					case COLLECTABLE:
						setCursor(actionCursor);
						break;
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				setCursor(Cursor.getDefaultCursor());
			}
		};

	}
	
	public void initializeGrid(MapSize size) {
		removeAll();
		isGridOn = false;
		rowCount = colCount = size.getValue();
		cells = new Tile[colCount][rowCount];
		//GridBagConstraints gbc = new GridBagConstraints();
		for (int col = 0; col < colCount; ++col) {
			for  (int row = 0; row < rowCount; ++row){
				//gbc.gridx = col;
				//gbc.gridy = row;
				
				Image img = graphics.getRandomTileDefaultBackgroundImage();
				Tile cell = new Tile(cellWidth, cellHeight, 1, img, new Point(col, row));
				cell.passability = TerrainPassability.UNOCCUPIED;
				cell.setBounds(col*32,row*32,32,32);
				add(cell);
				this.setLayer(cell,50);
				cells[col][row] = cell;
			}
		}
		updateUI();
	}

	private void reloadCells()
	{
		removeAll();
		for (int col = 0; col < colCount; ++col) {
			for  (int row = 0; row < rowCount; ++row){
				cells[col][row].setBounds(col*32,row*32,32, 32);
				this.add(cells[col][row]);
				this.setLayer(cells[col][row],50);
			}
		}
		this.updateUI();
	}

	public MouseListener getMouseListener()
	{
		return this.listener;
	}



	public void drawHeroRange(Point currentLocation, int range, MouseListener listener)
	{
		int minX = 0;
		int maxX = 0;
		int minY = 0;
		int maxY = 0;

		if(currentLocation.x - range>0)
		{
			minX = currentLocation.x - range;
		}
		else
		{
			minX = 0;
		}

		if(currentLocation.x + range<rowCount)
		{
			maxX = currentLocation.x + range;
		}
		else
		{
			maxX = rowCount;
		}

		if(currentLocation.y - range>0)
		{
			minY = currentLocation.y - range;
		}
		else
		{
			minY = 0;
		}

		if(currentLocation.y + range<colCount)
		{
			maxY = currentLocation.y + range;
		}
		else
		{
			maxY = colCount;
		}

		ArrayList<Point> moveRange = new ArrayList<Point>();
		for(int x=minX;x<maxX;x++)
		{
			for(int y=minY;y<maxY;y++)
			{
				int distance = Math.abs(cells[x][y].getDrawingPoint().x - currentLocation.x) + Math.abs(cells[x][y].getDrawingPoint().y - currentLocation.y);
				if(distance <= range && !cells[x][y].getOccupied())
				{
					moveRange.add(new Point(x,y));
				}
			}
		}
		for(Point p : moveRange)
		{
			MapRangeIndicator mri = new MapRangeIndicator(p,listener,cells[p.x][p.y].passability);
			mri.setVisible(true);
			this.add(mri);
			this.setLayer(mri,1000);
		}
	}

	public void clearHeroRange()
	{
		Component[] c = this.getComponents();
		for(Component cmp : c)
		{
			if(cmp.getClass().equals(MapRangeIndicator.class))
			{
				this.remove(cmp);
			}
		}
		this.updateUI();
		this.revalidate();
		this.repaint();
	}

	public void readSavedMap(SavedMap savedMap)
	{
		this.initializeGrid(savedMap.get_mapSize());
		this.cells = savedMap.get_cells();
		reloadCells();
		this.revalidate();
		this.repaint();
		Container m = this.getParent();
		m.revalidate();
		m.repaint();
	}

	public void drawHeroes(ArrayList<HeroInfo> heroes)
	{
		for (int col = 0; col < colCount; ++col)
		{
			for (int row = 0; row < rowCount; ++row)
			{
				if (cells[col][row].getOccupied() == false && cells[col][row].getMapObject() != null)
				{
					for(HeroInfo hero : heroes){
						if (cells[col][row].getMapObject().getClass().equals(hero.homeTown.getClass()))
						{
							//TODO: dobieranie wyglądu na podstawie miasta
							Point p = cells[col][row].getDrawingPoint();
							hero.currentPosition = p;
							hero.homeTown = (Town)cells[col][row].getMapObject();
							cells[col][row].heroOnTop();
							hero.townPosition = p;
							hero.heroDisplay = new HeroDisplayPanel(7);
							HeroDisplayPanel HeroD = hero.heroDisplay;
							HeroD.setBounds((p.x * 32)-48+16, (p.y * 32)-16, 96, 64);
							HeroD.setVisible(true);
							this.add(HeroD);
							this.setLayer(HeroD,150);
							this.updateUI();
						}
					}
				}
			}
		}
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
		if(isb!=null)
		{
			String category = isb.getSelectedCategory();
			String name = isb.getSelectedImageName();

			if(category!=null && category.equals("unit"))
			{
				NewUnitDialog nud = new NewUnitDialog();
				Army freeArmy = nud.showDialog();
				if(freeArmy!= null)
				{
					BufferedImage chunks[] = {freeArmy.getImage()};
					int centerX = x / cellWidth;
					int centerY = y / cellHeight;


					drawImageOnTiles(chunks, 1, 1, centerX, centerY, freeArmy);
				}
			}
			else
			{
				if (category == null || name == null)
				{
					System.out.println("Brak obrazka");
					return;
				} else
				{
					BufferedImage newImg = isb.getSelectedImage();
					MapObject mo = MapObject.makeMapObject(category, name, newImg);
					//Image newImg = mo.getImage();
					System.out.println("Nazwa obrazka: " + isb.getSelectedImageName());
					BufferedImage chunks[] = ImageProcessor.divideImage(newImg, cellWidth, cellHeight);

					int rows = newImg.getHeight(null) / cellHeight;
					int cols = newImg.getWidth(null) / cellWidth;
					int centerX = x / cellWidth;
					int centerY = y / cellHeight;

					if (!isImageWithinBonds(rows, cols, centerX, centerY))
						return;

					if (areTilesOccupied(rows, cols, centerX, centerY))
						return;

					drawImageOnTiles(chunks, rows, cols, centerX, centerY, mo);
				}
			}
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
		boolean isUnit = false;
		if(mo.getClass().equals(new Army(null).getClass()))
		{
			isUnit = true;
		}
		int count = 0;
		for (int r = 0; r < rows; ++r) {
			for(int c = 0; c < cols; ++c) {
				int _x = centerX + c - cols/2;
				int _y = centerY + r - rows+1;
				cells[_x][_y].setMapObject(new ImageIcon(chunks[count++]));
				cells[_x][_y].setMapObject(mo, new Point(_x, _y));
				cells[_x][_y].setCenterPosition(new Point(centerX,centerY));
				if(!isUnit)
				{
					cells[_x][_y].passability = TerrainPassability.OCCUPIED_PERM;
					cells[_x][_y].setOccupied(true);
				}
				else
				{
					cells[_x][_y].passability = TerrainPassability.ARMY;
					cells[_x][_y].setOccupied(false);
				}
				//cells[x][y].setType = town/mine/etc.
				//cells[x][y].canMove = false //exception- center(true)
				//or collect cells and put them in collection of object of i.e. town type
			}
		}
		if(!isUnit)
		{
			cells[centerX][centerY].setOccupied(false);
			cells[centerX][centerY].passability = TerrainPassability.COLLECTABLE;
		}
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

	public void moveHero(HeroInfo hero, Point target)
	{
		Pathfinder p = new Pathfinder(cells,hero.currentPosition,target);
		ArrayList<Point> pArr = p.generatePath();
		hero.heroDisplay.MoveHero(pArr);
	}

}
