import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;

public class MapGrid extends JPanel{
	
	private int rowCount;
	private int colCount;
	private int cellWidth;
	private int cellHeight;
	
	public MapGrid(int rowCount, int colCount, int cellWidth, int cellHeight){
		this.rowCount = rowCount;
		this.colCount = colCount;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < colCount; ++col) {
				gbc.gridx = col;
				gbc.gridy = row;
				
				CellGrid cell = new CellGrid(cellWidth, cellHeight);
				
				cell.setBackground(Color.GRAY);
				if (row < rowCount - 1) {
					if (col < colCount - 1) {
						cell.setBorder(new MatteBorder(1, 1, 0, 0, Color.black));
					}
					else {
						cell.setBorder(new MatteBorder(1, 1, 0, 1, Color.black));
					}
				}
				else {
					if (col < colCount - 1) {
						cell.setBorder(new MatteBorder(1, 1, 1, 0, Color.black));
					}
					else {
						cell.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
					}
				}
				
				add(cell, gbc);
			}
		}
	}
}
