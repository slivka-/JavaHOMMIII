import javax.swing.*;

public class TestClass {

	public static void main(String[] args) {
		
		Graphics g = new Graphics();
				
		MapGrid map = new MapGrid(25, 25, g);
		MapEditorMenuBar menuBar = new MapEditorMenuBar(map);
		
		GraphicsPanel gp = new GraphicsPanel();
		
		final DrawGUI draw = new DrawGUI(map, menuBar, gp);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				draw.drawAndShowMap();
			}
		});

	}

}
