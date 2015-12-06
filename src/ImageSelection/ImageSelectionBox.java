package ImageSelection;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageSelectionBox {

	private ArrayList<ArrayList<Image>> images;
	private int selectedIndex = -1;
	private int selectedList = -1;
	private Image selectedImage = null;
	private ImageSelectionBoxContainer isbc;
	private ArrayList<JList> imageLists;
	
	public ImageSelectionBox(ArrayList<ArrayList<Image>> images) {
		this.images = images;
		imageLists = new ArrayList<JList>();
		initializeLists();
	}
	
	public void setContainer(ImageSelectionBoxContainer isbc) {
		this.isbc = isbc;
	}
	
	private void initializeLists() {
		for (int i = 0; i < images.size(); ++i) {
			JList list = new JList(images.get(i).toArray());
			list.setCellRenderer(new ListRenderer());
			list.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					selectedIndex = imageLists.get(selectedList).getSelectedIndex();	
					selectedImage = getImageAtIndex(selectedIndex);
				}
			});
			imageLists.add(list);
		}
	}
	
	private void setImageList(int selectedList) {
		this.selectedList = selectedList;
		selectedIndex = -1;
		selectedImage = null;
		isbc.changeList(imageLists.get(selectedList));
	}
	
	private Image getImageAtIndex(int index) {
		return images.get(selectedList).get(index);
	}
	
	private class ListRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
			
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(images.get(selectedList).get(index)));
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
	
	
	public JList getList() {
		return imageLists.get(selectedList);
	}

}
