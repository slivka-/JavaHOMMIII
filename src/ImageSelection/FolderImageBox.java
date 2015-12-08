package ImageSelection;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class FolderImageBox extends JPanel {

	ArrayList<String> directories;
	private JList list;
	private int selectedIndex = -1;
	private ImageSelectionController controller;
	private JScrollPane pane;
	
	public FolderImageBox() {}

	public void setController(ImageSelectionController isc) {
		controller = isc;
	}
	
	public void setDirectoriesNames(ArrayList<String> directories) {
		this.directories = directories;
	}
	
	public void initialize() {
		list = new JList(directories.toArray());
		list.setCellRenderer(new ListRenderer());
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedIndex = list.getSelectedIndex();
				controller.setSelectedDirectory(selectedIndex);
			}
		});
		pane = new JScrollPane(list);
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(220, 200));

	}

	public JScrollPane getPane() {
		return pane;
	}
	
	private class ListRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
			
			JLabel label = new JLabel();
			label.setText(directories.get(index));
			// Selected item has red border
			if (isSelected) {
				label.setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));
			}
			else {
				label.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
			}
			
			return label;
		}
	}
}
