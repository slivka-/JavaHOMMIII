import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class MapGrid extends JPanel{
	
	private int rowCount;
	private int colCount;
	private final int cellWidth = 32;
	private final int cellHeight = 32;
	private CellGrid[][] cells;
	private boolean isGrid = false;
	
	public MapGrid(int rowCount, int colCount, Graphics g){
		this.rowCount = rowCount;
		this.colCount = colCount;
		cells = new CellGrid[rowCount][colCount];
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < colCount; ++col) {
				gbc.gridx = col;
				gbc.gridy = row;
				
				Image img = g.getRandomTileDefaultBackgroundImage();
				CellGrid cell = new CellGrid(cellWidth, cellHeight, img);

				add(cell, gbc);
				cells[row][col] = cell;
			}
		}
	}
	
	public void showHideGrid() {
		if (isGrid) {
			hideGrid();
		}
		else {
			drawGrid();
		}
	}
	
	private void drawGrid() {
		isGrid = true;
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
		isGrid = false;
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < colCount; ++col) {
				cells[row][col].setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}
}
