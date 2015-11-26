import java.awt.*;
import javax.swing.*;

public class DrawMapGrid {
	
	private MapGrid map;
	
	public DrawMapGrid(MapGrid map) {
		this.map = map;
	}
	
	public void drawAndShowMap() {
		JFrame frame = new JFrame("Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		//makes map scrollable
		JScrollPane scrollPane = new JScrollPane(map);
		
		frame.add(scrollPane);
		frame.setPreferredSize(new Dimension(800, 800));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
