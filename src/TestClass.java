import java.awt.Image;
import java.util.ArrayList;
import javax.swing.*;
import GraphicsProcessing.DrawGUI;
import GraphicsProcessing.Graphics;
import ImageSelection.*;
import Map.MapEditorMenuBar;
import Map.MapGrid;
import Map.MapGridContainer;
import Map.MapSize;


public class TestClass {

	public static void main(String[] args) {
		
		Graphics g = new Graphics();		
		
		ImageSelectionBox isb = new ImageSelectionBox(g.getListOfImages());
		FolderImageBox fib = new FolderImageBox();
		fib.setDirectoriesNames(g.getAllDirectoriesName());
		ImageSelectionController isc = new ImageSelectionController(isb, fib);
		
		FolderImageBoxContainer fibc = new FolderImageBoxContainer(fib);
		ImageSelectionBoxContainer isbc = new ImageSelectionBoxContainer(isb);
		ImageFolderComponent imgFolders = new ImageFolderComponent(fibc, isbc);
		
		MapGrid map = new MapGrid(g, isb);
		map.initializeGrid(MapSize.SMALL);
		MapEditorMenuBar menuBar = new MapEditorMenuBar(map);
		MapGridContainer mgc = new MapGridContainer(map);
		
		final DrawGUI draw = new DrawGUI(mgc, menuBar, imgFolders);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				draw.drawAndShowMap();
			}
		});
	}
}
