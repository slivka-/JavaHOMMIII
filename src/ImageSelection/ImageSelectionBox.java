package ImageSelection;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageSelectionBox extends JPanel{

	private int selectedIndex = -1;
	private int selectedList = -1;
	private Image selectedImage = null;
	private ArrayList<JList> imageLists;
	private JScrollPane pane;
	
	public ImageSelectionBox(ArrayList<ArrayList<Image>> images) {
		imageLists = new ArrayList<JList>();
		initialize();
		initializeLists(images);
	}

	private void initialize() {
		pane = new JScrollPane();
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(220, 600));
	}

	private void initializeLists(ArrayList<ArrayList<Image>> images) {
		for (int i = 0; i < images.size(); ++i) {
			JList list = new JList(images.get(i).toArray());
			list.setCellRenderer(new ListRenderer());
			list.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					selectedIndex = imageLists.get(selectedList).getSelectedIndex();
					selectedImage = (Image)imageLists.get(selectedList).getModel().getElementAt(selectedIndex);
				}
			});
			imageLists.add(list);
		}
	}
	
	private void setImageList(int selectedList) {
		this.selectedList = selectedList;
		selectedIndex = -1;
		selectedImage = null;
		pane.setViewportView(imageLists.get(selectedList));
		pane.validate();
		pane.repaint();
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
			label.setIcon(new ImageIcon((Image)imageLists.get(selectedList).getModel().getElementAt(index)));
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
	
	public Image getSelectedImage() {
		return selectedImage;
	}
	
	public void setImageSet(int listIndex) {
		setImageList(listIndex);		
	}

}
