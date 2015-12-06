package Map;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Do we need MapGrid map? Wouldn't it be better to use controller and
 * for example in the eShowGrid listener do MenuBarController.ShowGrid()
 */
public class MapEditorMenuBar extends JMenuBar {
	
	private MapGrid map;
	//private JMenu file;
	//private JMenu edit;
	
	public MapEditorMenuBar(MapGrid map) {
		this.map = map;
		createMenuBar();
	}
		
	private void createMenuBar() {
	
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		this.add(file);
		
		JMenuItem newMap = new JMenuItem("New");
		newMap.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				NewMapDialog nmd = new NewMapDialog();
				MapSize newMapSize = nmd.showDialog();
				if (newMapSize != null) {
					map.initializeGrid(newMapSize);
				}
			}
		});
		newMap.setToolTipText("Make a new map");
		newMap.setMnemonic(KeyEvent.VK_N);
		file.add(newMap);
		
		JMenuItem loadMap = new JMenuItem("Open");
		loadMap.setToolTipText("Open saved new map");
		loadMap.setMnemonic(KeyEvent.VK_O);
		file.add(loadMap);
		
		JMenuItem saveMap = new JMenuItem("Save");
		saveMap.setToolTipText("Save map");
		saveMap.setMnemonic(KeyEvent.VK_S);
		file.add(saveMap);		
		
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		this.add(edit);
		
		JMenuItem eShowGrid = new JMenuItem("Show/Hide grid");
		eShowGrid.setToolTipText("Shows/Hides grid on the map");
		eShowGrid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				map.showHideGrid();
			}
			
		});
		edit.add(eShowGrid);
	}
}
