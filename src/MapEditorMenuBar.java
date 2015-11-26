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
	
	public MapEditorMenuBar(MapGrid map) {
		this.map = map;
		createMenuBar();
	}
		
	private void createMenuBar() {
	
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		this.add(file);
		
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		this.add(edit);
		
		JMenuItem eShowGrid = new JMenuItem("Show grid");
		eShowGrid.setToolTipText("Show grid on the map");
		eShowGrid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				map.drawGrid();
			}
			
		});
		edit.add(eShowGrid);
	}
}
