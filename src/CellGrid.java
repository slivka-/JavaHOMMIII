import javax.swing.*;
import java.awt.*;

public class CellGrid extends JLabel{

	private int cellWidth;
	private int cellHeight;
	private MapGrid map;
	
	public CellGrid(int cellWidth, int cellHeight, Image img, MapGrid map) {
		super(new ImageIcon(img));
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;	
		this.map = map;
	}
		
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(cellWidth, cellHeight);
	}

}
