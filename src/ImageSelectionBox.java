import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageSelectionBox{

	private ArrayList<Image> images;
	private JList list;
	private int selectedIndex = -1;
	private Image selectedImage = null;
	
	public ImageSelectionBox(ArrayList<Image> images) {
		this.images = images;
		list = new JList(images.toArray());
		list.setCellRenderer(new ListRenderer());
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedIndex = list.getSelectedIndex();	
				selectedImage = getImageAtIndex(selectedIndex);
			}
		});
	}
	
	private Image getImageAtIndex(int index) {
		return images.get(index);
	}
	
	private class ListRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
			/*
			JLabel label = (JLabel) super.getListCellRendererComponent(
					list, value, index, isSelected, cellHasFocus);
					*/
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(images.get(index)));
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
	
	
	public JList getList() {
		return list;
	}

}
