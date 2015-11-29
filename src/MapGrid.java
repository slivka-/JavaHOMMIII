import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.image.BufferedImage;


public class MapGrid extends JPanel{
	
	private int rowCount;
	private int colCount;
	private final int cellWidth = 32;
	private final int cellHeight = 32;
	private CellGrid[][] cells;
	private boolean isGridOn = false;
	private Graphics graphics;
	private ImageSelectionBox isb;

	public MapGrid(Graphics g, ImageSelectionBox isb){
		this.graphics = g;
		this.isb = isb;
	}
	
	public void initializeGrid(MapSize size) {
		removeAll();
		rowCount = colCount = size.getValue();
		cells = new CellGrid[rowCount][colCount];
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();	
		for (int col = 0; col < colCount; ++col) {
			for  (int row = 0; row < rowCount; ++row){
				gbc.gridx = col;
				gbc.gridy = row;
				
				Image img = graphics.getRandomTileDefaultBackgroundImage();
				CellGrid cell = new CellGrid(cellWidth, cellHeight, img, this);
				add(cell, gbc);
				cells[col][row] = cell;
			}
		}
		updateUI();
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
		Image newImg = isb.getSelectedImage();
		if (newImg == null) {
			return;
		}
		else {
			BufferedImage chunks[] = ImageProcessor.divideImage(newImg, cellWidth, cellHeight);
			//for mines this is ideal
			newImg = chunks[chunks.length - 2];
			int _x = x / cellWidth;
			int _y = y / cellHeight;
			cells[_x][_y].setIcon(new ImageIcon(newImg));
		}
	}
	
	public void showHideGrid() {
		if (isGridOn) {
			hideGrid();
		}
		else {
			drawGrid();
		}
	}
	
	private void drawGrid() {
		isGridOn = true;
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < colCount; ++col) {
				
				MatteBorder b = null;
				
				if (row < rowCount - 1) {
					if (col < colCount - 1) {
						b = new MatteBorder(1, 1, 0, 0, Color.black);
					}
					else {
						b = new MatteBorder(1, 1, 0, 1, Color.black);
					}
				}
				else {
					if (col < colCount - 1) {
						b = new MatteBorder(1, 1, 1, 0, Color.black);
					}
					else {
						b = new MatteBorder(1, 1, 1, 1, Color.black);
					}
				}
				cells[row][col].setBorder(b);
			}
		}
	}
	
	private void hideGrid() {
		isGridOn = false;
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < colCount; ++col) {
				cells[row][col].setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}

}
