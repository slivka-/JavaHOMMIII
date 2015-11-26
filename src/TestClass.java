import java.awt.Image;

import javax.swing.*;

public class TestClass {

	public static void main(String[] args) {
		
		Graphics g = new Graphics();
				
		MapGrid map = new MapGrid(25, 25, g);
		final DrawMapGrid draw = new DrawMapGrid(map);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				draw.drawAndShowMap();
			}
		});

	}

}
