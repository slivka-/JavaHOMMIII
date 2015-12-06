package ImageSelection;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ImageSelectionBoxContainer extends JPanel{

	private ImageSelectionBox isb;
	private JScrollPane pane;
	
	public ImageSelectionBoxContainer(ImageSelectionBox isb) {
		this.isb = isb;
		isb.setContainer(this);
		//pane = new JScrollPane(isb.getList());
		pane = new JScrollPane();
		initialize();
	}
	
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(220, 600));
	}
	
	public void changeList(JList list) {
		pane.setViewportView(list);
		pane.validate();
		pane.repaint();
	}
	
	public JScrollPane getPane() {
		return pane;
	}
}
