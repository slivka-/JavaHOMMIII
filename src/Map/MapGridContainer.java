package Map;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


public class MapGridContainer extends JPanel{
	
	private MapGrid map;
	JScrollPane scrollPane;
	
	public MapGridContainer(MapGrid map) {
		this.map = map;
		scrollPane = new JScrollPane(map);
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		setSize(1000, 1000);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			    if(e.getButton() == MouseEvent.BUTTON1)
			    {
			    	map.changeCellImage(map.getMousePosition().x, map.getMousePosition().y);
			    }
				else if (e.getButton() == MouseEvent.BUTTON3)
				{
					//map.deleteTile(map.getMousePosition().x, map.getMousePosition().y);
					//for testing
					map.printTileID(map.getMousePosition().x, map.getMousePosition().y);
				}
				
			}
		});
	}
	
}
