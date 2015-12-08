package Map;
import javax.swing.*;
import java.awt.*;

public class Tile extends JLayeredPane{

	private int cellWidth;
	private int cellHeight;
	private boolean isOccupied;
	private int movementCost;
	private JLabel background;
	private JLabel object;
	
	public Tile(int cellWidth, int cellHeight, int movementCost, Image img) {
		//super(new ImageIcon(img));
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.movementCost = movementCost;
		this.setVisible(true);
		setBounds(0, 0, cellWidth, cellHeight);
		initializeBackground(img);
		initializeMapObject();
		isOccupied = false;
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
		updateUI();
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
		
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(cellWidth, cellHeight);
	}

}
