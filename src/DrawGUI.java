import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


public class DrawGUI {
	
	private MapGridContainer mgc;
	private MapEditorMenuBar menuBar;
	private GraphicsPanel gp;
	private ImageSelectionBox isb;
	
	public DrawGUI(MapGridContainer mgc, MapEditorMenuBar menuBar, GraphicsPanel gp, ImageSelectionBox isb) {
		this.mgc = mgc;
		this.menuBar = menuBar;
		this.gp = gp;
		this.isb = isb;
	}
	
	public void drawAndShowMap() {
		JFrame frame = new JFrame("Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		menuBar.setSize(800, 20);
		
		frame.getContentPane().add(mgc);
		
		//frame.getContentPane().add(gp, BorderLayout.EAST);		
		gp.setSize(200, 780);
		
		JScrollPane temp = new JScrollPane(isb.getList());
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		imagePanel.add(temp, BorderLayout.CENTER);
		frame.getContentPane().add(imagePanel, BorderLayout.EAST);

		frame.setPreferredSize(new Dimension(800, 800));
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
