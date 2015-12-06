package battleDisplay;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author slivka
 * Klasa rysuj¹ca t³o bitwy
 */
public class BackgroundPanel extends JPanel {
	
	private static final long serialVersionUID = 7882245762555727253L;
	
	private BufferedImage image;
	private Boolean drawGrid = false;
	
	/**
	 * @param src scie¿ka do pliku t³a
	 */
	public BackgroundPanel(String src){
		try {
			image = ImageIO.read(new File(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setBounds(1,1, image.getWidth(), image.getHeight());
		this.addMouseListener(new MouseListener() {
			
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
				if(drawGrid)
				{
					drawGrid = false;
				}
				else
				{
					drawGrid = true;
				}
				repaint();
			}
		});
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0, null);
		
		//Rysowanie siatki
		if(drawGrid){
			for(int i=0;i<11;i++)
			{	
				g2.drawLine(50, 110+43*i, 750, 110+43*i);
			}
			for(int j=0;j<15;j++)
			{
				g2.drawLine(50+50*j, 110, 50+50*j, 540);
				
			}
		}
	}
}
