import javax.swing.*;

public class TestClass {

	public static void main(String[] args) {
		
		Graphics g = new Graphics();
				
		MapGrid map = new MapGrid(g);
		map.initializeGrid(MapSize.SMALL);
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
