package battleDisplay;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author slivka
 * Klasa rysuj¹ca jednostki
 */
public class GraphicsPanel extends JPanel {

	private static final long serialVersionUID = 3688560623253859696L;
	private int _unitID;
	private BufferedImage image;
	private int _unitSize;
	private int _x;
	private int _y;
	
	public GraphicsPanel(String src, int x, int y, int unitID, int unitSize)
	{
		try {
			image = ImageIO.read(new File(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this._x = x;
		this._y = y;
		this._unitID = unitID;
		this._unitSize = unitSize;
		this.setBounds(_x-(image.getWidth()/2),_y-image.getHeight(), image.getWidth(), image.getHeight());
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
				System.out.println(unitID);
			}
		});;
	}
	/**
	 * Metoda odwracaj¹ca grafike jednostki
	 */
	public void flipFacing()
	{
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null),0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
		repaint();
	}
	/**
	 * Metoda przesuwaj¹ca jednostkê na now¹ pozycje
	 * @param p nowa pozycja jednostki
	 */
	public void movePanel(Point p)
	{
		//System.out.println((p.y-100)/43);
		int nextZIndex = 10 - ((p.y-100)/43);
		this.setBounds(p.x-(image.getWidth()/2),p.y-image.getHeight(), image.getWidth(), image.getHeight());
		this.getParent().setComponentZOrder(this, nextZIndex);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0, null);
	}
}
