import java.awt.*;
import javax.swing.*;


public class DrawGUI {
	
	private MapGrid map;
	private MapEditorMenuBar menuBar;
	private GraphicsPanel gp;
	
	public DrawGUI(MapGrid map, MapEditorMenuBar menuBar, GraphicsPanel gp) {
		this.map = map;
		this.menuBar = menuBar;
		this.gp = gp;
	}
	
	public void drawAndShowMap() {
		JFrame frame = new JFrame("Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		menuBar.setSize(800, 20);
		
		//makes map scrollable
		JPanel mapPanel = new JPanel();
		mapPanel.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(map);
		//scrollPane.setSize(600, 780);
		mapPanel.add(scrollPane, BorderLayout.CENTER);
		mapPanel.setSize(600, 780);
		frame.getContentPane().add(mapPanel);
		
		frame.getContentPane().add(gp, BorderLayout.EAST);		
		gp.setSize(200, 780);
		
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
