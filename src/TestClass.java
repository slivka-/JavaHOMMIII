import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;

public class TestClass {

	public static void main(String[] args) {
		
		Graphics g = new Graphics();
		g.loadAllDefaultImages();
		ArrayList<ArrayList<Image>> img = g.getListOfImages();
		ImageSelectionBox isb = new ImageSelectionBox(img.get(1));
		
		
		MapGrid map = new MapGrid(g, isb);
		map.initializeGrid(MapSize.SMALL);
		MapEditorMenuBar menuBar = new MapEditorMenuBar(map);
		MapGridContainer mgc = new MapGridContainer(map);
		
		GraphicsPanel gp = new GraphicsPanel();
		
		final DrawGUI draw = new DrawGUI(mgc, menuBar, gp, isb);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				draw.drawAndShowMap();
			}
		});
	}
}
