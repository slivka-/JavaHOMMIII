package ImageSelection;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class FolderImageBox {

	ArrayList<String> directories;
	private JList list;
	private int selectedIndex = -1;
	private ImageSelectionController controller;	
	
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
	}
	
	public JList getList() {
		return list;
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
