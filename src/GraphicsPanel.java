import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;


public class GraphicsPanel extends JPanel {
	
	public GraphicsPanel() {
		this.add(new JButton("Test Hello"));
		this.setMinimumSize(new Dimension(200, 780));
	}
}
