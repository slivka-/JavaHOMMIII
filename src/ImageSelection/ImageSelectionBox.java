package ImageSelection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageSelectionBox extends JPanel{

	private int selectedIndex = -1;
	private BufferedImage selectedImage = null;
	private JScrollPane pane;
	private HashMap<String, HashMap<String, BufferedImage>> listOfImagesByCategory;
	private String selectedImageName = null;
	private String selectedCategory = null;
	private HashMap<String, JList> imageLists;


	public ImageSelectionBox(HashMap<String, HashMap<String, BufferedImage>> images) {
		listOfImagesByCategory = images;
		imageLists = new HashMap<String, JList>();
		initialize();
		initializeLists(images);
	}

	private void initialize() {
		pane = new JScrollPane();
		this.setLayout(new BorderLayout());
		this.add(pane, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(220, 600));
	}


	/***
	 * Initialize list with images from given folders. Adds listener on value changed for getting currently
	 * selected image.
	 * @param images
	 */
	private void initializeLists(HashMap<String, HashMap<String, BufferedImage>> images) {
		for (Map.Entry<String, HashMap<String, BufferedImage>> entry : images.entrySet()) {
			JList list = new JList(entry.getValue().keySet().toArray());
			list.setCellRenderer(new MyListRenderer());
			list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					selectedIndex = imageLists.get(selectedCategory).getSelectedIndex();
					selectedImageName = (String) imageLists.get(selectedCategory).getModel().getElementAt(selectedIndex);
					selectedImage = listOfImagesByCategory.get(selectedCategory).get(selectedImageName);
				}
			});
			imageLists.put(entry.getKey(), list);
		}
	}

	/***
	 * Class for helping rendering list with images in ImageSelectionBox
	 */
	public class MyListRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(
					list, value, index, isSelected, cellHasFocus);

			//selectedImageName = (String)value;
			label.setIcon(new ImageIcon(listOfImagesByCategory.get(selectedCategory).get(value)));
			label.setHorizontalTextPosition(JLabel.CENTER);
			label.setVerticalTextPosition(JLabel.BOTTOM);
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


	/***
	 * Changes displayed images in imageSelectionBox to those in given category(folder)
	 * @param cat Category(folder) within which images are stored
	 */
	private void setCategoryImageList(String cat) {
		selectedCategory = cat;
		selectedImage = null;
		pane.setViewportView(imageLists.get(selectedCategory));
		pane.validate();
		pane.repaint();
	}

	public JScrollPane getPane() {
		return pane;
	}

	public BufferedImage getSelectedImage() {
		return selectedImage;
	}

	public String getSelectedImageName() {
		return selectedImageName;
	}

	public void setCategory(String cat) {
		setCategoryImageList(cat);
	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelctedToUnit()
	{
		this.selectedCategory = "unit";
	}

}
