package Map;
import javax.swing.*;

public class NewMapDialog {

	private JComponent[] components;
	private JComboBox mapSize;
	
	public NewMapDialog() {
		mapSize = new JComboBox(MapSize.values());
		components = new JComponent[] {
				new JLabel("Map size"),
				mapSize,
				new JLabel("Make new map?")
		};
	}
	
	public MapSize showDialog() {
		
		int choice = JOptionPane.showConfirmDialog(null, components, "New map", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			return (MapSize)mapSize.getSelectedItem();
		}
		else {
			return null;
		}
	}
	
	
}
