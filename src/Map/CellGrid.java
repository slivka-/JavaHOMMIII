package Map;
import javax.swing.*;
import java.awt.*;

public class CellGrid extends JLabel{

	private int cellWidth;
	private int cellHeight;
	
	public CellGrid(int cellWidth, int cellHeight, Image img) {
		super(new ImageIcon(img));
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;	
	}
		
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(cellWidth, cellHeight);
	}

}
