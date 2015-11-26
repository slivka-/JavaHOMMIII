import javax.swing.*;

public class TestClass {

	public static void main(String[] args) {
		MapGrid map = new MapGrid(5, 5, 50, 50);
		final DrawMapGrid draw = new DrawMapGrid(map);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				draw.drawAndShowMap();
			}
		});

	}

}
