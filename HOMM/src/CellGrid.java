import javax.swing.*;
import java.awt.*;

public class CellGrid extends JPanel{

	private int cellWidth;
	private int cellHeight;
	
	public CellGrid(int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(cellWidth, cellHeight);
	}
}
