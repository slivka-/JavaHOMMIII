package GraphicsProcessing;
import java.awt.*;

import javax.swing.*;

import HeroDisplay.HeroDisplayPanel;
import ImageSelection.ImageFolderComponent;
import Map.MapEditorMenuBar;
import Map.MapGridContainer;


public class DrawGUI {
	
	private MapGridContainer mgc;
	private MapEditorMenuBar menuBar;
	private ImageFolderComponent imgFolders;
	
	public DrawGUI(MapGridContainer mgc, MapEditorMenuBar menuBar, ImageFolderComponent imgFolders)
	{
		this.mgc = mgc;
		this.menuBar = menuBar;
		this.imgFolders = imgFolders;
	}

	public DrawGUI(MapGridContainer mgc, MapEditorMenuBar menuBar)
	{
		this.mgc = mgc;
		this.menuBar = menuBar;
		this.imgFolders = null;
	}
	
	public void drawAndShowMap()
	{
		JFrame frame = new JFrame("Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		menuBar.setSize(800, 20);
		
		frame.getContentPane().add(mgc);
		if(imgFolders!= null)
		{
			frame.getContentPane().add(imgFolders, BorderLayout.EAST);
		}
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
